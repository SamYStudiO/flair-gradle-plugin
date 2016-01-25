package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
enum Properties
{
	MODULE_NAME( "moduleName" ) ,
	PACKAGE_NAME( "packageName" ) ,
	AUTO_GENERATE_VARIANT_DIRECTORIES( "autoGenerateVariantDirectories" ) ,

	DEBUG( "debug" ),
	X86( "x86" ),

	GENERATE_ATF_TEXTURES_FROM_DRAWABLES( "generateAtfTexturesFromDrawables" ),

	APP_ID( "appId" ) ,
	APP_ID_SUFFIX( "appIdSuffix" ) ,
	APP_NAME( "appName" ) ,
	APP_NAME_SUFFIX( "appNameSuffix" ) ,
	APP_FILE_NAME( "appFileName" ) ,
	APP_VERSION( "appVersion" ) ,
	APP_FULL_SCREEN( "appFullScreen" ) ,
	APP_ASPECT_RATIO( "appAspectRatio" ) ,
	APP_AUTO_ORIENT( "appAutoOrient" ) ,
	APP_DEPTH_AND_STENCIL( "appDepthAndStencil" ) ,
	APP_DEFAULT_SUPPORTED_LANGUAGES( "appDefaultSupportedLanguages" ) ,

	COMPILE_MAIN_CLASS( "compileMainClass" ) ,
	COMPILE_OPTIONS( "compileOptions" ) ,

	PACKAGE_TARGET( "packageTarget" ) ,
	PACKAGE_EXCLUDE_RESOURCES( "packageExcludeResources" ) ,
	PACKAGE_OPTIONS( "packageOptions" ) ,

	EMULATOR_SCREEN_SIZE( "emulatorScreenSize" ) ,
	EMULATOR_SCREEN_DPI( "emulatorScreenDpi" )


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
