/*
 Feathers
 Copyright 2012-2015 Joshua Tynjala. All Rights Reserved.

 This program is free software. You can redistribute and/or modify it in
 accordance with the terms of the accompanying license agreement.
 */
package feathers.themes
{
	import feathers.skins.StyleNameFunctionStyleProvider;
	import feathers.skins.StyleProviderRegistry;

	import starling.events.EventDispatcher;

	/**
	 * Base class for themes that pass a <code>StyleNameFunctionStyleProvider</code>
	 * to each component class.
	 *
	 * @see feathers.skins.StyleNameFunctionStyleProvider
	 * @see ../../../help/skinning.html Skinning Feathers components
	 * @see ../../../help/custom-themes.html Creating custom Feathers themes
	 */
	public class StyleNameFunctionTheme extends EventDispatcher
	{
		/**
		 * @private
		 */
		protected var _registry : StyleProviderRegistry;

		/**
		 * Constructor.
		 */
		public function StyleNameFunctionTheme()
		{
			this._registry = new StyleProviderRegistry();
		}

		/**
		 * Disposes the theme.
		 */
		public function dispose() : void
		{
			if( this._registry )
			{
				this._registry.dispose();
				this._registry = null;
			}
		}

		/**
		 * Returns a <code>StyleNameFunctionStyleProvider</code> to be passed to
		 * the specified class.
		 */
		public function getStyleProviderForClass( type : Class ) : StyleNameFunctionStyleProvider
		{
			return StyleNameFunctionStyleProvider( this._registry.getStyleProvider( type ) );
		}
	}
}
