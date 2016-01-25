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

	private List<String> packageExcludeResources

	private List<String> packageOptions = new ArrayList<>( )

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

	public List<String> getPackageExcludeResources()
	{
		return packageExcludeResources
	}

	public void packageExcludeResources( List<String> packageExcludeResources )
	{
		this.packageExcludeResources = packageExcludeResources
	}

	public List<String> getPackageOptions()
	{
		return packageOptions
	}

	public void packageOption( String packageOption )
	{
		this.packageOptions.add( packageOption )
	}

	public void packageOptions( List<String> packageOptions )
	{
		this.packageOptions.addAll( packageOptions )
	}

	public void packageOptions( String... packageOptions )
	{
		this.packageOptions.addAll( packageOptions )
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
				case Properties.APP_ID.name: return extensionManager.getFlairProperty( Properties.PACKAGE_NAME.name )
				case Properties.APP_ID_SUFFIX.name: return ""
				case Properties.APP_NAME.name: return project.name
				case Properties.APP_NAME_SUFFIX.name: return ""
				case Properties.APP_FILE_NAME.name: return getProp( Properties.APP_NAME.name , true ) + getProp( Properties.APP_NAME_SUFFIX.name , true )
				case Properties.APP_VERSION.name: return "1.0.0"
				case Properties.APP_FULL_SCREEN.name: return true
				case Properties.APP_ASPECT_RATIO.name: return "any"
				case Properties.APP_AUTO_ORIENT.name: return true
				case Properties.APP_DEPTH_AND_STENCIL.name: return false
				case Properties.APP_DEFAULT_SUPPORTED_LANGUAGES.name: return "en"

				case Properties.COMPILE_MAIN_CLASS.name:
					String packageName = extensionManager.getFlairProperty( Properties.PACKAGE_NAME.name )

					switch( p )
					{
						case Platforms.IOS: return packageName + ".MainIOS"
						case Platforms.ANDROID: return packageName + ".MainAndroid"
						case Platforms.DESKTOP: return packageName + ".MainDesktop"

						default: return null
					}
				case Properties.COMPILE_OPTIONS.name: return new ArrayList<String>( )

				case Properties.EMULATOR_SCREEN_SIZE.name: return "540x960:540x960"
				case Properties.EMULATOR_SCREEN_DPI.name: return 240

				case Properties.PACKAGE_EXCLUDE_RESOURCES.name:
					switch( p )
					{
						case Platforms.IOS: return [ "drawable*-ldpi*/**" , "drawable*-mdpi*/**" , "drawable*-hdpi*/**" , "drawable*-xxxhdpi*/**" ]
						case Platforms.ANDROID: return [ "drawable*-ldpi*/**" , "drawable*-xxxhdpi*/**" ]
						case Platforms.DESKTOP: return [ "drawable*-ldpi*/**" , "drawable*-hdpi*/**" , "drawable*-xxhdpi*/**" , "drawable*-xxxhdpi*/**" ]

						default: return [ "drawable*-ldpi*/**" , "drawable*-xxxhdpi*/**" ]
					}
				case Properties.PACKAGE_OPTIONS.name: return new ArrayList<String>( )

				default: return null
			}
		}
	}
}
