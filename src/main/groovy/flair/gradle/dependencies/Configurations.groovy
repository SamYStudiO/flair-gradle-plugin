package flair.gradle.dependencies

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Configurations {
	SOURCE( "source" , "src/main/actionscript" , "src/main/fonts" , "src/main/generated" ) ,
	LIBRARY( "library" , [ dir: "libs_swc" , include: "**/*.swc" ] ) ,
	AS_LIBRARY( "asLibrary" , "libs_as" ) ,
	NATIVE_LIBRARY( "nativeLibrary" , [ dir: "libs_ane" , include: "**/*.ane" ] ) ,
	PACKAGE( "pack" , "src/main/assets" ) ,

	IOS_COMPILE( "iosSource" , "src/ios/actionscript" , "src/ios/fonts" ) ,
	IOS_LIBRARY_COMPILE( "iosLibrary" ) ,
	IOS_AS_LIBRARY_COMPILE( "iosAsLibrary" ) ,
	IOS_NATIVE_COMPILE( "iosNativeLibrary" ) ,
	IOS_PACKAGE( "iosPack" , "src/ios/assets" ) ,

	ANDROID_COMPILE( "androidSource" , "src/android/actionscript" , "src/android/fonts" ) ,
	ANDROID_LIBRARY_COMPILE( "androidLibrary" ) ,
	ANDROID_AS_LIBRARY_COMPILE( "androidAsLibrary" ) ,
	ANDROID_NATIVE_COMPILE( "androidNativeLibrary" ) ,
	ANDROID_PACKAGE( "androidPack" , "src/android/assets" ) ,

	DESKTOP_COMPILE( "desktopSource" , "src/desktop/actionscript" , "src/desktop/fonts" ) ,
	DESKTOP_LIBRARY_COMPILE( "desktopLibrary" ) ,
	DESKTOP_AS_LIBRARY_COMPILE( "desktopAsLibrary" ) ,
	DESKTOP_NATIVE_COMPILE( "desktopNativeLibrary" ) ,
	DESKTOP_PACKAGE( "desktopPack" , "src/desktop/assets" ) ,

	public static final List<Configurations> DEFAULTS = [ SOURCE ,
														  LIBRARY ,
														  AS_LIBRARY ,
														  NATIVE_LIBRARY ,
														  PACKAGE ]

	private String name

	private String[] files

	private Map<String , String> fileTree

	public Configurations( String name )
	{
		this.name = name
	}

	public Configurations( String name , String... files )
	{
		this.name = name
		this.files = files
	}

	public Configurations( String name , Map<String , String> fileTree , String... files )
	{
		this.name = name
		this.files = files
		this.fileTree = fileTree
	}

	public String getName()
	{
		return name
	}

	public String[] getFiles()
	{
		return files
	}

	public Map<String , String> getFileTree()
	{
		return fileTree
	}
}