package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
enum FlairProperties
{
	MODULE_NAME( "moduleName" ) ,
	PACKAGE_NAME( "packageName" ) ,

	DEBUG( "debug" ) ,
	AUTO_GENERATE_VARIANT_DIRECTORIES( "autoGenerateVariantDirectories" ) ,
	GENERATE_ATF_TEXTURES_FROM_DRAWABLES( "generateAtfTexturesFromDrawables" ) ,

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
	PACKAGE_X86( "packageX86" ) ,
	PACKAGE_EXCLUDE_RESOURCES( "packageExcludeResources" ) ,
	PACKAGE_CONNECT( "packageConnect" ) ,
	PACKAGE_LISTEN( "packageListen" ) ,
	PACKAGE_SAMPLER( "packageSampler" ) ,
	PACKAGE_HIDE_ANE_LIB_SYMBOLS( "packageHideAneLibSymbols" ) ,

	SIGNING_ALIAS( "signingAlias" ),
	SIGNING_STORE_TYPE( "signingStoreType" ),
	SIGNING_KEY_STORE( "signingKeyStore" ),
	SIGNING_STORE_PASS( "signingStorePass" ),
	SIGNING_KEY_PASS( "signingKeyPass" ),
	SIGNING_PROVIDER_NAME( "signingProviderName" ),
	SIGNING_TSA( "signingTsa" ),
	SIGNING_PROVISIONING_PROFILE( "signingProvisioningProfile" ),

	EMULATOR_SCREEN_SIZE( "emulatorScreenSize" ) ,
	EMULATOR_SCREEN_DPI( "emulatorScreenDpi" )

	private String name

	FlairProperties( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}
