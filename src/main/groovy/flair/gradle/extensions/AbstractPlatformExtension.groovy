package flair.gradle.extensions

import flair.gradle.plugins.PluginManager
import flair.gradle.variants.Platforms
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlatformExtension extends AbstractExtension implements IPlatformExtension
{
	private Platforms platform

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

	private Boolean appAutoOrient

	private Boolean appDepthAndStencil

	private String appDefaultSupportedLanguages

	private String compileMainClass

	private List<String> compileOptions = new ArrayList<>( )

	private String emulatorScreenSize

	private Integer emulatorScreenDpi

	private String packageTarget

	private Boolean packageX86

	private List<String> packageExcludeResources

	private String packageConnect

	private String packageListen

	private Boolean packageSampler

	private Boolean packageHideAneLibSymbols

	public AbstractPlatformExtension( String name , Project project , Platforms platform )
	{
		super( name , project )

		this.platform = platform
	}

	@Override
	public Platforms getPlatform()
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

	public Boolean getAppAutoOrient()
	{
		return appAutoOrient
	}

	public void appAutoOrient( Boolean appAutoOrient )
	{
		this.appAutoOrient = appAutoOrient
	}

	public Boolean getAppDepthAndStencil()
	{
		return appDepthAndStencil
	}

	public void appDepthAndStencil( Boolean appDepthAndStencil )
	{
		this.appDepthAndStencil = appDepthAndStencil
	}

	public String getAppDefaultSupportedLanguages()
	{
		return appDefaultSupportedLanguages
	}

	public void appDefaultSupportedLanguages( String appDefaultSupportedLanguages )
	{
		this.appDefaultSupportedLanguages = appDefaultSupportedLanguages
	}

	public String getCompileMainClass()
	{
		return compileMainClass
	}

	public void compileMainClass( String compileMainClass )
	{
		this.compileMainClass = compileMainClass
	}

	public List<String> getCompileOptions()
	{
		return compileOptions
	}

	public void compileOption( String compileOption )
	{
		this.compileOptions.add( compileOption )
	}

	public void compileOptions( List<String> compileOptions )
	{
		this.compileOptions.addAll( compileOptions )
	}

	public void compileOptions( String... compileOptions )
	{
		this.compileOptions.addAll( compileOptions )
	}

	public String getEmulatorScreenSize()
	{
		return emulatorScreenSize
	}

	public void emulatorScreenSize( String emulatorScreenSize )
	{
		this.emulatorScreenSize = emulatorScreenSize
	}

	public Integer getEmulatorScreenDpi()
	{
		return emulatorScreenDpi
	}

	public void emulatorScreenDpi( Integer emulatorScreenDpi )
	{
		this.emulatorScreenDpi = emulatorScreenDpi
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

	public void packageExcludeResources( List<String> packageExcludeResources )
	{
		this.packageExcludeResources = packageExcludeResources
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

	@Override
	public IExtension getExtension( String name )
	{
		return this[ name ] as IExtension
	}

	@Override
	public Object getProp( String property , boolean returnDefaultIfNull )
	{
		if( this[ property ] || !returnDefaultIfNull ) return this[ property ] else
		{
			Platforms p = platform

			if( p == null ) p = PluginManager.getCurrentPlatforms( project )[ 0 ]

			switch( property )
			{
				case FlairProperties.DEBUG.name: return false
				case FlairProperties.GENERATE_ATF_TEXTURES_FROM_DRAWABLES.name: return false

				case FlairProperties.APP_ID.name: return extensionManager.getFlairProperty( FlairProperties.PACKAGE_NAME.name )
				case FlairProperties.APP_ID_SUFFIX.name: return ""
				case FlairProperties.APP_NAME.name: return project.name
				case FlairProperties.APP_NAME_SUFFIX.name: return ""
				case FlairProperties.APP_FILE_NAME.name: return getProp( FlairProperties.APP_NAME.name , true ) + getProp( FlairProperties.APP_NAME_SUFFIX.name , true )
				case FlairProperties.APP_VERSION.name: return "1.0.0"
				case FlairProperties.APP_FULL_SCREEN.name: return true
				case FlairProperties.APP_ASPECT_RATIO.name: return "any"
				case FlairProperties.APP_AUTO_ORIENT.name: return true
				case FlairProperties.APP_DEPTH_AND_STENCIL.name: return false
				case FlairProperties.APP_DEFAULT_SUPPORTED_LANGUAGES.name: return "en"

				case FlairProperties.COMPILE_MAIN_CLASS.name:
					String packageName = extensionManager.getFlairProperty( FlairProperties.PACKAGE_NAME.name )

					switch( p )
					{
						case Platforms.IOS: return packageName + ".MainIOS"
						case Platforms.ANDROID: return packageName + ".MainAndroid"
						case Platforms.DESKTOP: return packageName + ".MainDesktop"

						default: return null
					}
				case FlairProperties.COMPILE_OPTIONS.name: return new ArrayList<String>( )

				case FlairProperties.EMULATOR_SCREEN_SIZE.name: return "540x960:540x960"
				case FlairProperties.EMULATOR_SCREEN_DPI.name: return 240

				case FlairProperties.PACKAGE_TARGET.name: return null
				case FlairProperties.PACKAGE_X86.name: return false
				case FlairProperties.PACKAGE_EXCLUDE_RESOURCES.name:
					switch( p )
					{
						case Platforms.IOS: return [ "drawable*-ldpi*/**" , "drawable*-mdpi*/**" , "drawable*-hdpi*/**" , "drawable*-xxxhdpi*/**" ]
						case Platforms.ANDROID: return [ "drawable*-ldpi*/**" , "drawable*-xxxhdpi*/**" ]
						case Platforms.DESKTOP: return [ "drawable*-ldpi*/**" , "drawable*-hdpi*/**" , "drawable*-xxhdpi*/**" , "drawable*-xxxhdpi*/**" ]

						default: return [ "drawable*-ldpi*/**" , "drawable*-xxxhdpi*/**" ]
					}


				case FlairProperties.PACKAGE_CONNECT: return null
				case FlairProperties.PACKAGE_LISTEN: return null
				case FlairProperties.PACKAGE_SAMPLER: return false
				case FlairProperties.PACKAGE_HIDE_ANE_LIB_SYMBOLS: return false


				default: return null
			}
		}
	}
}
