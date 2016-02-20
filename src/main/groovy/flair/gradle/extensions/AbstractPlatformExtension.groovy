package flair.gradle.extensions

import flair.gradle.plugins.PluginManager
import flair.gradle.variants.Platform
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlatformExtension extends AbstractExtension implements IPlatformExtension
{
	private Platform platform

	private Boolean debug

	private Boolean generateAtfTexturesFromDrawables

	private String appId

	private String appIdSuffix

	private String appName

	private String appNameSuffix

	private String appFileName

	private String appVersion

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

	private List<String> packageExcludeResources = new ArrayList<String>()

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

	public AbstractPlatformExtension( String name , Project project , Platform platform )
	{
		super( name , project )

		this.platform = platform
	}

	@Override
	public Platform getPlatform()
	{
		return platform
	}

	public Boolean getDebug()
	{
		return debug
	}

	public void debug( Boolean debug )
	{
		this.debug = debug
	}

	public Boolean getGenerateAtfTexturesFromDrawables()
	{
		return generateAtfTexturesFromDrawables
	}

	public void generateAtfTexturesFromDrawables( Boolean generateAtfTexturesFromDrawables )
	{
		this.generateAtfTexturesFromDrawables = generateAtfTexturesFromDrawables
	}

	public String getAppId()
	{
		return appId
	}

	public void appId( String appId )
	{
		this.appId = appId
	}

	public String getAppIdSuffix()
	{
		return appIdSuffix
	}

	public void appIdSuffix( String appIdSuffix )
	{
		this.appIdSuffix = appIdSuffix
	}

	public String getAppName()
	{
		return appName
	}

	public void appName( String appName )
	{
		this.appName = appName
	}

	public String getAppNameSuffix()
	{
		return appNameSuffix
	}

	public void appNameSuffix( String appNameSuffix )
	{
		this.appNameSuffix = appNameSuffix
	}

	public String getAppFileName()
	{
		return appFileName
	}

	public void appFileName( String appFileName )
	{
		this.appFileName = appFileName
	}

	public String getAppVersion()
	{
		return appVersion
	}

	public void appVersion( String appVersion )
	{
		this.appVersion = appVersion
	}

	public Boolean getAppFullScreen()
	{
		return appFullScreen
	}

	public void appFullScreen( Boolean appFullScreen )
	{
		this.appFullScreen = appFullScreen
	}

	public String getAppAspectRatio()
	{
		return appAspectRatio
	}

	public void appAspectRatio( String appAspectRatio )
	{
		this.appAspectRatio = appAspectRatio
	}

	public Boolean getAppAutoOrients()
	{
		return appAutoOrients
	}

	public void appAutoOrients( Boolean appAutoOrients )
	{
		this.appAutoOrients = appAutoOrients
	}

	public Boolean getAppDepthAndStencil()
	{
		return appDepthAndStencil
	}

	public void appDepthAndStencil( Boolean appDepthAndStencil )
	{
		this.appDepthAndStencil = appDepthAndStencil
	}

	public String getAppDefaultSupportedLanguage()
	{
		return appDefaultSupportedLanguage
	}

	public void appDefaultSupportedLanguage( String appDefaultSupportedLanguage )
	{
		this.appDefaultSupportedLanguage = appDefaultSupportedLanguage
	}

	public String getCompilerMainClass()
	{
		return compilerMainClass
	}

	public void compilerMainClass( String compilerMainClass )
	{
		this.compilerMainClass = compilerMainClass
	}

	public List<String> getCompilerOptions()
	{
		return compilerOptions
	}

	public void compilerOption( String compilerOption )
	{
		this.compilerOptions.add( compilerOption )
	}

	public void compileOptions( List<String> compilerOptions )
	{
		this.compilerOptions.addAll( compilerOptions )
	}

	public void compilerOptions( String... compilerOptions )
	{
		this.compilerOptions.addAll( compilerOptions )
	}

	public String getAdlScreenSize()
	{
		return adlScreenSize
	}

	public void adlScreenSize( String adlScreenSize )
	{
		this.adlScreenSize = adlScreenSize
	}

	public Integer getAdlScreenDpi()
	{
		return adlScreenDpi
	}

	public void adlScreenDpi( Integer adlScreenDpi )
	{
		this.adlScreenDpi = adlScreenDpi
	}

	public String getAdlPubId()
	{
		return adlPubId
	}

	public void adlPubId( String adlPubId )
	{
		this.adlPubId = adlPubId
	}

	public Boolean getAdlNoDebug()
	{
		return adlNoDebug
	}

	public void adlNoDebug( Boolean adlNoDebug )
	{
		this.adlNoDebug = adlNoDebug
	}

	public Boolean getAdlAtLogin()
	{
		return adlAtLogin
	}

	public void adlAtLogin( Boolean adlAtLogin )
	{
		this.adlAtLogin = adlAtLogin
	}

	public List<String> getAdlParameters()
	{
		return adlParameters
	}

	public void adlParameter( String adlParameter )
	{
		this.adlParameters.add( adlParameter )
	}

	public void adlParameters( List<String> adlParameters )
	{
		this.adlParameters.addAll( adlParameters )
	}

	public void adlParameters( String... adlParameters )
	{
		this.adlParameters.addAll( adlParameters )
	}

	public String getPackageFileName()
	{
		return packageFileName
	}

	public void packageFileName( String packageFileName )
	{
		this.packageFileName = packageFileName
	}

	public String getPackageTarget()
	{
		return packageTarget
	}

	public void packageTarget( String packageTarget )
	{
		this.packageTarget = packageTarget
	}

	public Boolean getPackageX86()
	{
		return packageX86
	}

	public void packageX86( Boolean packageX86 )
	{
		this.packageX86 = packageX86
	}

	public List<String> getPackageExcludeResources()
	{
		return packageExcludeResources
	}

	public void packageExcludeResource( String packageExcludeResource )
	{
		this.packageExcludeResources.add( packageExcludeResource )
	}

	public void packageExcludeResources( List<String> packageExcludeResources )
	{
		this.packageExcludeResources.addAll( packageExcludeResources )
	}

	public void packageExcludeResources( String... packageExcludeResources )
	{
		this.packageExcludeResources.addAll( packageExcludeResources )
	}

	public String getPackageConnect()
	{
		return packageConnect
	}

	public void packageConnect( String packageConnect )
	{
		this.packageConnect = packageConnect
	}

	public String getPackageListen()
	{
		return packageListen
	}

	public void packageListen( String packageListen )
	{
		this.packageListen = packageListen
	}

	public void packageListen( int packageListen )
	{
		this.packageListen = packageListen.toString( )
	}

	public Boolean getPackageSampler()
	{
		return packageSampler
	}

	public void packageSampler( Boolean packageSampler )
	{
		this.packageSampler = packageSampler
	}

	public Boolean getPackageHideAneLibSymbols()
	{
		return packageHideAneLibSymbols
	}

	public void packageHideAneLibSymbols( Boolean packageHideAneLibSymbols )
	{
		this.packageHideAneLibSymbols = packageHideAneLibSymbols
	}

	public Boolean getPackagePlatformSdk()
	{
		return packagePlatformSdk
	}

	public void packagePlatformSdk( String packagePlatformSdk )
	{
		this.packagePlatformSdk = packagePlatformSdk
	}

	public String getSigningAlias()
	{
		return signingAlias
	}

	public void signingAlias( String signingAlias )
	{
		this.signingAlias = signingAlias
	}

	public String getSigningStoreType()
	{
		return signingStoreType
	}

	public void signingStoreType( String signingStoreType )
	{
		this.signingStoreType = signingStoreType
	}

	public String getSigningKeyStore()
	{
		return signingKeyStore
	}

	public void signingKeyStore( String signingKeyStore )
	{
		this.signingKeyStore = signingKeyStore
	}

	public void signingKeyStore( File file )
	{
		this.signingKeyStore = file.path
	}

	public String getSigningStorePass()
	{
		return signingStorePass
	}

	public void signingStorePass( String signingStorePass )
	{
		this.signingStorePass = signingStorePass
	}

	public String getSigningKeyPass()
	{
		return signingKeyPass
	}

	public void signingKeyPass( String signingKeyPass )
	{
		this.signingKeyPass = signingKeyPass
	}

	public String getSigningProviderName()
	{
		return signingProviderName
	}

	public void signingProviderName( String signingProviderName )
	{
		this.signingProviderName = signingProviderName
	}

	public String getSigningTsa()
	{
		return signingTsa
	}

	public void signingTsa( String signingTsa )
	{
		this.signingTsa = signingTsa
	}

	public String getSigningProvisioningProfile()
	{
		return signingProvisioningProfile
	}

	public void signingProvisioningProfile( String signingProvisioningProfile )
	{
		this.signingProvisioningProfile = signingProvisioningProfile
	}

	public void signingProvisioningProfile( File file )
	{
		this.signingProvisioningProfile = file.path
	}

	@Override
	public IExtension getExtension( String name )
	{
		return this[ name ] as IExtension
	}

	@Override
	public Object getProp( String property , Variant variant , boolean returnDefaultIfNull )
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
				case FlairProperty.APP_FILE_NAME.name: return project.name
				case FlairProperty.APP_VERSION.name: return "1.0.0"
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

				case FlairProperty.PACKAGE_FILE_NAME.name: return "${ project.name }_${ variant.getProductFlavorsBuildTypeWithType( Variant.NamingTypes.UNDERSCORE ) }_${ extensionManager.getFlairProperty( variant , FlairProperty.APP_VERSION ) }"
				case FlairProperty.PACKAGE_TARGET.name:
					switch( p )
					{
						case Platform.IOS: return extensionManager.getFlairProperty( variant , FlairProperty.DEBUG ) ? "ipa-debug" : "ipa-test"
						case Platform.ANDROID: return extensionManager.getFlairProperty( variant , FlairProperty.DEBUG ) ? "apk-debug" : "apk-captive-runtime"
						case Platform.DESKTOP: return "native"
						default: return null
					}
				case FlairProperty.PACKAGE_X86.name: return false
				case FlairProperty.PACKAGE_EXCLUDE_RESOURCES.name:
					switch( p )
					{
						case Platform.IOS: return [ "drawable*-ldpi*/**" , "drawable*-mdpi*/**" , "drawable*-hdpi*/**" , "drawable*-xxxhdpi*/**" ]
						case Platform.ANDROID: return [ "drawable*-ldpi*/**" , "drawable*-xxxhdpi*/**" ]
						case Platform.DESKTOP: return [ "drawable*-ldpi*/**" , "drawable*-hdpi*/**" , "drawable*-xxhdpi*/**" , "drawable*-xxxhdpi*/**" ]
						default: return [ "drawable*-ldpi*/**" , "drawable*-xxxhdpi*/**" ]
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

	private String getSigningFilePath( Variant variant , String type )
	{
		File f = getSigningFile( variant , type )

		return f ? "${ project.buildDir.path }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }/signing/${ f.name }" : null
	}
}
