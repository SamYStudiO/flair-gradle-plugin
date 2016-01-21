package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
enum Properties
{
	MODULE_NAME( "moduleName" ) ,
	PACKAGE_NAME( "packageName" ) ,
	AUTO_GENERATE_VARIANT_DIRECTORIES( "autoGenerateVariantDirectories" ) ,

	MAIN_CLASS( "mainClass" ) ,
	EXCLUDE_RESOURCES( "excludeResources" ) ,

	ADL_SCREEN_SIZE( "screensize" ) ,
	ADL_X_SCREEN_DPI( "XscreenDPI" ) ,

	APP_ID( "id" ) ,
	APP_ID_SUFFIX( "idSuffix" ) ,
	APP_NAME( "appName" ) ,
	APP_NAME_SUFFIX( "appNameSuffix" ) ,
	APP_FILE_NAME( "filename" ) ,
	APP_VERSION( "version" ) ,
	APP_FULL_SCREEN( "fullScreen" ) ,
	APP_ASPECT_RATIO( "aspectRatio" ) ,
	APP_AUTO_ORIENT( "autoOrient" ) ,
	APP_DEPTH_AND_STENCIL( "depthAndStencil" ) ,
	APP_DEFAULT_SUPPORTED_LANGUAGES( "defaultSupportedLanguages" ) ,

	GENERATE_ATF_TEXTURES_FROM_ATLASES( "generateAtfTexturesFromAtlases" )

	private String name

	Properties( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}
