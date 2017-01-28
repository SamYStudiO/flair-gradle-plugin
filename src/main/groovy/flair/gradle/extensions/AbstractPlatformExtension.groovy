package flair.gradle.extensions

import flair.gradle.plugins.PluginManager
import flair.gradle.utils.Platform
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
abstract class AbstractPlatformExtension extends AbstractExtension implements IPlatformExtension
{
	private Platform platform

	private Boolean debug

	private Boolean generateAtfTexturesFromDrawables

	private String appId

	private String appIdSuffix

	private String appName

	private String appNameSuffix

	private String appFileName

	private String appVersionLabel

	private String appVersionNumber

	private Boolean appFullScreen

	private String appAspectRatio

	private Boolean appAutoOrients

	private Boolean appDepthAndStencil

	private String appDefaultSupportedLanguage

	private String compilerMainClass

	private List<String> compilerOptions = new ArrayList<String>()

	private String adlScreenSize

	private Integer adlScreenDpi

	private String adlPubId

	private Boolean adlNoDebug

	private Boolean adlAtLogin

	private List<String> adlParameters = new ArrayList<String>()

	private String packageFileName

	private String packageTarget

	private Boolean packageX86

	private List<String> packageExcludeDrawables = new ArrayList<String>( )

	private String packageConnect

	private String packageListen

	private Boolean packageSampler

	private Boolean packageHideAneLibSymbols

	private Boolean packagePlatformSdk

	private String signingAlias

	private String signingStoreType

	private String signingKeyStore

	private String signingStorePass

	private String signingKeyPass

	private String signingProviderName

	private String signingTsa

	private String signingProvisioningProfile

	AbstractPlatformExtension( String name , Project project , Platform platform )
	{
		super( name , project )

		this.platform = platform
	}

	@Override
	Platform getPlatform()
	{
		return platform
	}

	Boolean getDebug()
	{
		return debug
	}

	void debug( Boolean debug )
	{
		this.debug = debug
	}

	Boolean getGenerateAtfTexturesFromDrawables()
	{
		return generateAtfTexturesFromDrawables
	}

	void generateAtfTexturesFromDrawables( Boolean generateAtfTexturesFromDrawables )
	{
		this.generateAtfTexturesFromDrawables = generateAtfTexturesFromDrawables
	}

	String getAppId()
	{
		return appId
	}

	void appId( String appId )
	{
		this.appId = appId
	}

	String getAppIdSuffix()
	{
		return appIdSuffix
	}

	void appIdSuffix( String appIdSuffix )
	{
		this.appIdSuffix = appIdSuffix
	}

	String getAppName()
	{
		return appName
	}

	void appName( String appName )
	{
		this.appName = appName
	}

	String getAppNameSuffix()
	{
		return appNameSuffix
	}

	void appNameSuffix( String appNameSuffix )
	{
		this.appNameSuffix = appNameSuffix
	}

	String getAppFileName()
	{
		return appFileName
	}

	void appFileName( String appFileName )
	{
		this.appFileName = appFileName
	}

	String getAppVersionNumber()
	{
		return appVersionNumber
	}

	void appVersionNumber( String appVersionNumber )
	{
		this.appVersionNumber = appVersionNumber
	}

	String getAppVersionLabel()
	{
		return appVersionLabel
	}

	void appVersionLabel( String appVersionLabel )
	{
		this.appVersionLabel = appVersionLabel
	}

	Boolean getAppFullScreen()
	{
		return appFullScreen
	}

	void appFullScreen( Boolean appFullScreen )
	{
		this.appFullScreen = appFullScreen
	}

	String getAppAspectRatio()
	{
		return appAspectRatio
	}

	void appAspectRatio( String appAspectRatio )
	{
		this.appAspectRatio = appAspectRatio
	}

	Boolean getAppAutoOrients()
	{
		return appAutoOrients
	}

	void appAutoOrients( Boolean appAutoOrients )
	{
		this.appAutoOrients = appAutoOrients
	}

	Boolean getAppDepthAndStencil()
	{
		return appDepthAndStencil
	}

	void appDepthAndStencil( Boolean appDepthAndStencil )
	{
		this.appDepthAndStencil = appDepthAndStencil
	}

	String getAppDefaultSupportedLanguage()
	{
		return appDefaultSupportedLanguage
	}

	void appDefaultSupportedLanguage( String appDefaultSupportedLanguage )
	{
		this.appDefaultSupportedLanguage = appDefaultSupportedLanguage
	}

	String getCompilerMainClass()
	{
		return compilerMainClass
	}

	void compilerMainClass( String compilerMainClass )
	{
		this.compilerMainClass = compilerMainClass
	}

	List<String> getCompilerOptions()
	{
		return compilerOptions.clone( ) as List<String>
	}

	void compilerOption( String compilerOption )
	{
		this.compilerOptions.add( compilerOption )
	}

	void compileOptions( List<String> compilerOptions )
	{
		this.compilerOptions.addAll( compilerOptions )
	}

	void compilerOptions( String... compilerOptions )
	{
		this.compilerOptions.addAll( compilerOptions )
	}

	String getAdlScreenSize()
	{
		return adlScreenSize
	}

	void adlScreenSize( String adlScreenSize )
	{
		this.adlScreenSize = adlScreenSize
	}

	Integer getAdlScreenDpi()
	{
		return adlScreenDpi
	}

	void adlScreenDpi( Integer adlScreenDpi )
	{
		this.adlScreenDpi = adlScreenDpi
	}

	String getAdlPubId()
	{
		return adlPubId
	}

	void adlPubId( String adlPubId )
	{
		this.adlPubId = adlPubId
	}

	Boolean getAdlNoDebug()
	{
		return adlNoDebug
	}

	void adlNoDebug( Boolean adlNoDebug )
	{
		this.adlNoDebug = adlNoDebug
	}

	Boolean getAdlAtLogin()
	{
		return adlAtLogin
	}

	void adlAtLogin( Boolean adlAtLogin )
	{
		this.adlAtLogin = adlAtLogin
	}

	List<String> getAdlParameters()
	{
		return adlParameters.clone( ) as List<String>
	}

	void adlParameter( String adlParameter )
	{
		this.adlParameters.add( adlParameter )
	}

	void adlParameters( List<String> adlParameters )
	{
		this.adlParameters.addAll( adlParameters )
	}

	void adlParameters( String... adlParameters )
	{
		this.adlParameters.addAll( adlParameters )
	}

	String getPackageFileName()
	{
		return packageFileName
	}

	void packageFileName( String packageFileName )
	{
		this.packageFileName = packageFileName
	}

	String getPackageTarget()
	{
		return packageTarget
	}

	void packageTarget( String packageTarget )
	{
		this.packageTarget = packageTarget
	}

	Boolean getPackageX86()
	{
		return packageX86
	}

	void packageX86( Boolean packageX86 )
	{
		this.packageX86 = packageX86
	}

	List<String> getPackageExcludeDrawables()
	{
		return packageExcludeDrawables.clone( ) as List<String>
	}

	void packageExcludeDrawable( String packageExcludeDrawable )
	{
		this.packageExcludeDrawables.add( packageExcludeDrawable )
	}

	void packageExcludeDrawables( List<String> packageExcludeDrawables )
	{
		this.packageExcludeDrawables.addAll( packageExcludeDrawables )
	}

	void packageExcludeDrawables( String... packageExcludeDrawables )
	{
		this.packageExcludeDrawables.addAll( packageExcludeDrawables )
	}

	String getPackageConnect()
	{
		return packageConnect
	}

	void packageConnect( String packageConnect )
	{
		this.packageConnect = packageConnect
	}

	String getPackageListen()
	{
		return packageListen
	}

	void packageListen( String packageListen )
	{
		this.packageListen = packageListen
	}

	void packageListen( int packageListen )
	{
		this.packageListen = packageListen.toString( )
	}

	Boolean getPackageSampler()
	{
		return packageSampler
	}

	void packageSampler( Boolean packageSampler )
	{
		this.packageSampler = packageSampler
	}

	Boolean getPackageHideAneLibSymbols()
	{
		return packageHideAneLibSymbols
	}

	void packageHideAneLibSymbols( Boolean packageHideAneLibSymbols )
	{
		this.packageHideAneLibSymbols = packageHideAneLibSymbols
	}

	Boolean getPackagePlatformSdk()
	{
		return packagePlatformSdk
	}

	void packagePlatformSdk( String packagePlatformSdk )
	{
		this.packagePlatformSdk = packagePlatformSdk
	}

	String getSigningAlias()
	{
		return signingAlias
	}

	void signingAlias( String signingAlias )
	{
		this.signingAlias = signingAlias
	}

	String getSigningStoreType()
	{
		return signingStoreType
	}

	void signingStoreType( String signingStoreType )
	{
		this.signingStoreType = signingStoreType
	}

	String getSigningKeyStore()
	{
		return signingKeyStore
	}

	void signingKeyStore( String signingKeyStore )
	{
		this.signingKeyStore = signingKeyStore
	}

	void signingKeyStore( File file )
	{
		this.signingKeyStore = file.path
	}

	String getSigningStorePass()
	{
		return signingStorePass
	}

	void signingStorePass( String signingStorePass )
	{
		this.signingStorePass = signingStorePass
	}

	String getSigningKeyPass()
	{
		return signingKeyPass
	}

	void signingKeyPass( String signingKeyPass )
	{
		this.signingKeyPass = signingKeyPass
	}

	String getSigningProviderName()
	{
		return signingProviderName
	}

	void signingProviderName( String signingProviderName )
	{
		this.signingProviderName = signingProviderName
	}

	String getSigningTsa()
	{
		return signingTsa
	}

	void signingTsa( String signingTsa )
	{
		this.signingTsa = signingTsa
	}

	String getSigningProvisioningProfile()
	{
		return signingProvisioningProfile
	}

	void signingProvisioningProfile( String signingProvisioningProfile )
	{
		this.signingProvisioningProfile = signingProvisioningProfile
	}

	void signingProvisioningProfile( File file )
	{
		this.signingProvisioningProfile = file.path
	}

	@Override
	IExtension getExtension( String name )
	{
		return this[ name ] as IExtension
	}

	@Override
	Object getProp( String property , Variant variant , boolean returnDefaultIfNull )
	{
		if( this[ property ] || !returnDefaultIfNull ) return this[ property ] else
		{
			Platform p = platform

			if( p == null ) p = PluginManager.getCurrentPlatforms( project )[ 0 ]

			switch( property )
			{
				case FlairProperty.DEBUG.name: return false
				case FlairProperty.GENERATE_ATF_TEXTURES_FROM_DRAWABLES.name: return false

				case FlairProperty.APP_ID.name: return extensionManager.getFlairProperty( FlairProperty.PACKAGE_NAME )
				case FlairProperty.APP_ID_SUFFIX.name: return ""
				case FlairProperty.APP_NAME.name: return project.name
				case FlairProperty.APP_NAME_SUFFIX.name: return ""
				case FlairProperty.APP_FILE_NAME.name: return formatProjectName()
				case FlairProperty.APP_VERSION_LABEL.name: return "1.0.0"
				case FlairProperty.APP_VERSION_NUMBER.name: return "1"
				case FlairProperty.APP_FULL_SCREEN.name: return true
				case FlairProperty.APP_ASPECT_RATIO.name: return "any"
				case FlairProperty.APP_AUTO_ORIENTS.name: return true
				case FlairProperty.APP_DEPTH_AND_STENCIL.name: return false
				case FlairProperty.APP_DEFAULT_SUPPORTED_LANGUAGES.name: return "en"

				case FlairProperty.COMPILER_MAIN_CLASS.name:
					String packageName = extensionManager.getFlairProperty( FlairProperty.PACKAGE_NAME )
					switch( p )
					{
						case Platform.IOS: return packageName + ".MainIOS"
						case Platform.ANDROID: return packageName + ".MainAndroid"
						case Platform.DESKTOP: return packageName + ".MainDesktop"
						default: return null
					}
				case FlairProperty.COMPILER_OPTIONS.name: return new ArrayList<String>()

				case FlairProperty.ADL_SCREEN_SIZE.name: return "540x920:540x960"
				case FlairProperty.ADL_SCREEN_DPI.name: return p == Platform.IOS ? 200 : 240
				case FlairProperty.ADL_PUB_ID.name: return null
				case FlairProperty.ADL_NO_DEBUG.name: return false
				case FlairProperty.ADL_AT_LOGIN.name: return false
				case FlairProperty.ADL_PARAMETERS.name: return new ArrayList<String>()

				case FlairProperty.PACKAGE_FILE_NAME.name: return "${ formatProjectName() }_${ variant.getName( Variant.NamingType.UNDERSCORE , false ) }_${ extensionManager.getFlairProperty( variant , FlairProperty.APP_VERSION_LABEL ) }_${ extensionManager.getFlairProperty( variant , FlairProperty.APP_VERSION_NUMBER ) }".toLowerCase(  )
				case FlairProperty.PACKAGE_TARGET.name:
					switch( p )
					{
						case Platform.IOS: return extensionManager.getFlairProperty( variant , FlairProperty.DEBUG ) ? "ipa-debug" : "ipa-test"
						case Platform.ANDROID: return extensionManager.getFlairProperty( variant , FlairProperty.DEBUG ) ? "apk-debug" : "apk-captive-runtime"
						case Platform.DESKTOP: return "native"
						default: return null
					}
				case FlairProperty.PACKAGE_X86.name: return false
				case FlairProperty.PACKAGE_EXCLUDE_DRAWABLES.name:
					switch( p )
					{
						case Platform.IOS: return [ "ldpi" , "hdpi" , "xxxhdpi" ]
						case Platform.ANDROID: return [ "ldpi" , "xxxhdpi" ]
						case Platform.DESKTOP: return [ "ldpi" , "hdpi" , "xxhdpi" , "xxxhdpi" ]
						default: return [ ]
					}
				case FlairProperty.PACKAGE_CONNECT.name: return null
				case FlairProperty.PACKAGE_LISTEN.name: return extensionManager.getFlairProperty( variant , FlairProperty.DEBUG ) ? "7936" : null
				case FlairProperty.PACKAGE_SAMPLER.name: return false
				case FlairProperty.PACKAGE_HIDE_ANE_LIB_SYMBOLS.name: return false
				case FlairProperty.PACKAGE_PLATFORM_SDK.name: return null

				case FlairProperty.SIGNING_ALIAS.name: return null
				case FlairProperty.SIGNING_KEY_PASS.name: return null
				case FlairProperty.SIGNING_PROVIDER_NAME.name: return null
				case FlairProperty.SIGNING_TSA.name: return null
				case FlairProperty.SIGNING_STORE_TYPE.name: return "pkcs12"
				case FlairProperty.SIGNING_KEY_STORE.name: return extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_STORE_TYPE ) == "pkcs12" ? getSigningFilePath( variant , "p12" ) : null
				case FlairProperty.SIGNING_STORE_PASS.name:
					File f = getSigningFile( variant , "txt" )
					return f && f.exists( ) ? f.text.trim( ) : null
				case FlairProperty.SIGNING_PROVISIONING_PROFILE.name: return p == Platform.IOS ? getSigningFilePath( variant , "mobileprovision" ) : null

				default: return null
			}
		}
	}

	private File getSigningFile( Variant variant , String type )
	{
		String subFolder
		String target = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_TARGET )

		switch( true )
		{
			case variant.platform == Platform.IOS && target == "ipa-app-store":
				subFolder = "store"
				break
			case variant.platform == Platform.IOS && target == "ipa-ad-hoc":
				subFolder = "adhoc"
				break

			case variant.platform == Platform.IOS:
				subFolder = "development"
				break
			default:
				subFolder = ""
				break
		}

		List<File> list = new ArrayList<File>( )

		variant.directories.each {
			list.add( project.file( "${ extensionManager.getFlairProperty( FlairProperty.MODULE_NAME ) }/src/${ it }/signing/${ subFolder }" ) )
		}

		list = list.reverse( )

		for( File file : list )
		{
			if( file.exists( ) )
			{
				File f = file.listFiles( ).find { it.name.split( "\\." )[ 1 ].toLowerCase( ) == type }

				if( f ) return f
			}
		}

		return null
	}

	private String formatProjectName( )
	{
		String name = project.name
		name = name.replaceAll( " " , "_" ).replaceAll( /[a-z]([A-Z])/ , /_$0/ )

		return name.toLowerCase( )
	}

	private String getSigningFilePath( Variant variant , String type )
	{
		File f = getSigningFile( variant , type )

		return f ? "${ project.buildDir.path }/${ variant.name }/signing/${ f.name }" : null
	}
}
