// =================================================================================================
//
//	Starling Framework
//	Copyright Gamua GmbH. All Rights Reserved.
//
//	This program is free software. You can redistribute and/or modify it
//	in accordance with the terms of the accompanying license agreement.
//
// =================================================================================================

package starling.text
{
    import flash.geom.Matrix;
    import flash.text.AntiAliasType;
    import flash.text.Font;
    import flash.text.FontStyle;
    import flash.text.TextField;

    import starling.display.MeshBatch;
    import starling.display.Quad;
    import starling.textures.Texture;
    import starling.utils.Align;

    /** @private
     *
     *  <p>This text compositor uses a Flash TextField to render system- or embedded fonts into
     *  a texture.</p>
     */
    internal class TrueTypeCompositor implements ITextCompositor
    {
        // helpers
        private static var sHelperMatrix:Matrix = new Matrix();
        private static var sHelperQuad:Quad = new Quad(100, 100);
        private static var sNativeTextField:flash.text.TextField = new flash.text.TextField();
        private static var sNativeFormat:flash.text.TextFormat = new flash.text.TextFormat();
        private static var sEmbeddedFonts:Array = null;

        /** Creates a new TrueTypeCompositor instance. */
        public function TrueTypeCompositor()
        { }

        /** @inheritDoc */
        public function fillMeshBatch(meshBatch:MeshBatch, width:Number, height:Number, text:String,
                                      format:TextFormat, options:TextOptions=null):void
        {
            if (text == null || text == "") return;

            var texture:Texture;
            var textureFormat:String = options.textureFormat;
            var bitmapData:BitmapDataEx = renderText(width, height, text, format, options);

            texture = Texture.fromBitmapData(bitmapData, false, false, bitmapData.scale, textureFormat);
            texture.root.onRestore = function():void
            {
                bitmapData = renderText(width, height, text, format, options);
                texture.root.uploadBitmapData(bitmapData);
                bitmapData.dispose();
                bitmapData = null;
            };

            bitmapData.dispose();
            bitmapData = null;

            sHelperQuad.texture = texture;
            sHelperQuad.readjustSize();

            if (format.horizontalAlign == Align.LEFT) sHelperQuad.x = 0;
            else if (format.horizontalAlign == Align.CENTER) sHelperQuad.x = int((width - texture.width) / 2);
            else sHelperQuad.x = width - texture.width;

            if (format.verticalAlign == Align.TOP) sHelperQuad.y = 0;
            else if (format.verticalAlign == Align.CENTER) sHelperQuad.y = int((height - texture.height) / 2);
            else sHelperQuad.y = height - texture.height;

            meshBatch.addMesh(sHelperQuad);

            sHelperQuad.texture = null;
        }

        /** @inheritDoc */
        public function clearMeshBatch(meshBatch:MeshBatch):void
        {
            meshBatch.clear();
            if (meshBatch.texture) meshBatch.texture.dispose();
        }

        private function renderText(width:Number, height:Number, text:String,
                                    format:TextFormat, options:TextOptions):BitmapDataEx
        {
            var scaledWidth:Number  = width  * options.textureScale;
            var scaledHeight:Number = height * options.textureScale;
            var hAlign:String = format.horizontalAlign;

            format.toNativeFormat(sNativeFormat);

            sNativeFormat.size = Number(sNativeFormat.size) * options.textureScale;
            sNativeTextField.embedFonts = isEmbeddedFont(format);
            sNativeTextField.defaultTextFormat = sNativeFormat;
            sNativeTextField.width  = scaledWidth;
            sNativeTextField.height = scaledHeight;
            sNativeTextField.antiAliasType = AntiAliasType.ADVANCED;
            sNativeTextField.selectable = false;
            sNativeTextField.multiline = true;
            sNativeTextField.wordWrap = options.wordWrap;

            if (options.isHtmlText) sNativeTextField.htmlText = text;
            else                    sNativeTextField.text     = text;

            if (options.autoScale)
                autoScaleNativeTextField(sNativeTextField, text, options.isHtmlText);

            var textWidth:Number  = sNativeTextField.textWidth;
            var textHeight:Number = sNativeTextField.textHeight;
            var bitmapWidth:int   = Math.ceil(textWidth)  + 4;
            var bitmapHeight:int  = Math.ceil(textHeight) + 4;
            var maxTextureSize:int = Texture.maxSize;
            var minTextureSize:int = 1;
            var offsetX:Number = 0.0;

            // HTML text may have its own alignment -> use the complete width
            if (options.isHtmlText) textWidth = bitmapWidth = scaledWidth;

            // check for invalid texture sizes
            if (bitmapWidth  < minTextureSize) bitmapWidth  = 1;
            if (bitmapHeight < minTextureSize) bitmapHeight = 1;
            if (bitmapHeight > maxTextureSize || bitmapWidth > maxTextureSize)
            {
                options.textureScale *= maxTextureSize / Math.max(bitmapWidth, bitmapHeight);
                return renderText(width, height, text, format, options);
            }
            else
            {
                if (!options.isHtmlText)
                {
                    if      (hAlign == Align.RIGHT)  offsetX =  scaledWidth - textWidth - 4;
                    else if (hAlign == Align.CENTER) offsetX = (scaledWidth - textWidth - 4) / 2.0;
                }

                // finally: draw TextField to bitmap data
                var bitmapData:BitmapDataEx = new BitmapDataEx(bitmapWidth, bitmapHeight);
                sHelperMatrix.setTo(1, 0, 0, 1, -offsetX, 0);
                bitmapData.draw(sNativeTextField, sHelperMatrix);
                bitmapData.scale = options.textureScale;
                sNativeTextField.text = "";
                return bitmapData;
            }
        }

        private function autoScaleNativeTextField(textField:flash.text.TextField,
                                                  text:String, isHtmlText:Boolean):void
        {
            var textFormat:flash.text.TextFormat = textField.defaultTextFormat;
            var maxTextWidth:int  = textField.width  - 4;
            var maxTextHeight:int = textField.height - 4;
            var size:Number = Number(textFormat.size);

            while (textField.textWidth > maxTextWidth || textField.textHeight > maxTextHeight)
            {
                if (size <= 4) break;

                textFormat.size = size--;
                textField.defaultTextFormat = textFormat;

                if (isHtmlText) textField.htmlText = text;
                else            textField.text     = text;
            }
        }

        /** Updates the list of embedded fonts. To be called when a font is loaded at runtime. */
        public static function updateEmbeddedFonts():void
        {
            sEmbeddedFonts = null; // will be updated in 'isEmbeddedFont()'
        }

        private static function isEmbeddedFont(format:TextFormat):Boolean
        {
            if (sEmbeddedFonts == null)
                sEmbeddedFonts = Font.enumerateFonts(false);

            for each (var font:Font in sEmbeddedFonts)
            {
                var style:String = font.fontStyle;
                var isBold:Boolean = style == FontStyle.BOLD || style == FontStyle.BOLD_ITALIC;
                var isItalic:Boolean = style == FontStyle.ITALIC || style == FontStyle.BOLD_ITALIC;

                if (format.font == font.fontName && format.italic == isItalic && format.bold == isBold)
                    return true;
            }

            return false;
        }
    }
}

import flash.display.BitmapData;

class BitmapDataEx extends BitmapData
{
    private var _scale:Number = 1.0;

    function BitmapDataEx(width:int, height:int, transparent:Boolean=true, fillColor:uint=0x0)
    {
        super(width, height, transparent, fillColor);
    }

    public function get scale():Number { return _scale; }
    public function set scale(value:Number):void { _scale = value; }
}
