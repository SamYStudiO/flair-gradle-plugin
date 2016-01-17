package flair.resources
{
	import starling.textures.Texture;

	/**
	 *
	 */
	public function getDrawable( assetID : String , groupID : String = null ) : Texture
	{
		return getAssetManager( groupID ).getTexture( assetID );
	}
}
