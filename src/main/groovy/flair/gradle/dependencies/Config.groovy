package flair.gradle.dependencies

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
enum Config {
	SOURCE( "source" , [ "src/main/actionscript" , "src/main/fonts" , "src/main/generated" ] ) ,
	LIBRARY( "library" , null , [ dir: "libs_swc" , include: "**/*.swc" ] ) ,
	AS_LIBRARY( "asLibrary" , [ "libs_as" ] ) ,
	NATIVE_LIBRARY( "nativeLibrary" , null , [ dir: "libs_ane" , include: "**/*.ane" ] ) ,
	PACKAGE( "pack" , [ "src/main/assets" ] ) ,

	IOS_SOURCE( "iosSource" , [ "src/ios/actionscript" , "src/ios/fonts" ] ) ,
	IOS_LIBRARY( "iosLibrary" ) ,
	IOS_AS_LIBRARY( "iosAsLibrary" ) ,
	IOS_NATIVE_LIBRARY( "iosNativeLibrary" ) ,
	IOS_PACKAGE( "iosPack" , [ "src/ios/assets" ] ) ,

	ANDROID_SOURCE( "androidSource" , [ "src/android/actionscript" , "src/android/fonts" ] ) ,
	ANDROID_LIBRARY( "androidLibrary" ) ,
	ANDROID_AS_LIBRARY( "androidAsLibrary" ) ,
	ANDROID_NATIVE_LIBRARY( "androidNativeLibrary" ) ,
	ANDROID_PACKAGE( "androidPack" , [ "src/android/assets" ] ) ,

	DESKTOP_SOURCE( "desktopSource" , [ "src/desktop/actionscript" , "src/desktop/fonts" ] ) ,
	DESKTOP_LIBRARY( "desktopLibrary" ) ,
	DESKTOP_AS_LIBRARY( "desktopAsLibrary" ) ,
	DESKTOP_NATIVE_LIBRARY( "desktopNativeLibrary" ) ,
	DESKTOP_PACKAGE( "desktopPack" , [ "src/desktop/assets" ] ) ,

	public static final List<Config> DEFAULTS = [ SOURCE ,
												  LIBRARY ,
												  AS_LIBRARY ,
												  NATIVE_LIBRARY ,
												  PACKAGE ]

	private String name

	private List<String> files

	private Map<String , String> fileTree

	Config( String name )
	{
		this.name = name
	}

	Config( String name , List<String> files , Map<String , String> fileTree = null )
	{
		this.name = name
		this.files = files
		this.fileTree = fileTree
	}

	String getName()
	{
		return name
	}

	List<String> getFiles()
	{
		return files
	}

	Map<String , String> getFileTree()
	{
		return fileTree
	}
}