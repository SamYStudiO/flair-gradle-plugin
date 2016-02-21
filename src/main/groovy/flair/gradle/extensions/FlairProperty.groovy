package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
enum FlairProperty
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
	APP_AUTO_ORIENTS( "appAutoOrients" ) ,
	APP_DEPTH_AND_STENCIL( "appDepthAndStencil" ) ,
	APP_DEFAULT_SUPPORTED_LANGUAGES( "appDefaultSupportedLanguage" ) ,

	COMPILER_MAIN_CLASS( "compilerMainClass" ) ,
	COMPILER_OPTIONS( "compilerOptions" ) ,

	PACKAGE_FILE_NAME( "packageFileName" ) ,
	PACKAGE_TARGET( "packageTarget" ) ,
	PACKAGE_X86( "packageX86" ) ,
	PACKAGE_EXCLUDE_DRAWABLES( "packageExcludeDrawables" ) ,
	PACKAGE_CONNECT( "packageConnect" ) ,
	PACKAGE_LISTEN( "packageListen" ) ,
	PACKAGE_SAMPLER( "packageSampler" ) ,
	PACKAGE_HIDE_ANE_LIB_SYMBOLS( "packageHideAneLibSymbols" ) ,
	PACKAGE_PLATFORM_SDK( "packagePlatformSdk" ) ,

	SIGNING_ALIAS( "signingAlias" ) ,
	SIGNING_STORE_TYPE( "signingStoreType" ) ,
	SIGNING_KEY_STORE( "signingKeyStore" ) ,
	SIGNING_STORE_PASS( "signingStorePass" ) ,
	SIGNING_KEY_PASS( "signingKeyPass" ) ,
	SIGNING_PROVIDER_NAME( "signingProviderName" ) ,
	SIGNING_TSA( "signingTsa" ) ,
	SIGNING_PROVISIONING_PROFILE( "signingProvisioningProfile" ) ,

	ADL_SCREEN_SIZE( "adlScreenSize" ) ,
	ADL_SCREEN_DPI( "adlScreenDpi" ) ,
	ADL_PUB_ID( "adlPubId" ) ,
	ADL_NO_DEBUG( "adlNoDebug" ) ,
	ADL_AT_LOGIN( "adlAtLogin" ) ,
	ADL_PARAMETERS( "adlParameters" )

	private String name

	FlairProperty( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}
