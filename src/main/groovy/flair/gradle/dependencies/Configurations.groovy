package flair.gradle.dependencies

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Configurations {
	COMPILE( "compile" , "src/main/actionscript" , "src/main/fonts" , "src/main/generated" ) ,
	LIBRARY_COMPILE( "libraryCompile" , [ dir: "libs_swc" , include: "**/*.swc" ] ) ,
	AS_LIBRARY_COMPILE( "asLibraryCompile" , "libs_as" ) ,
	NATIVE_COMPILE( "nativeCompile" , [ dir: "libs_ane" , include: "**/*.ane" ] ) ,
	PACKAGE( "pack" , "src/main/assets" ) ,

	IOS_COMPILE( "iosCompile" , "src/ios/actionscript" , "src/ios/fonts" ) ,
	IOS_LIBRARY_COMPILE( "iosLibraryCompile" ) ,
	IOS_AS_LIBRARY_COMPILE( "iosAsLibraryCompile" ) ,
	IOS_NATIVE_COMPILE( "iosNativeCompile" ) ,
	IOS_PACKAGE( "iosPack" , "src/ios/assets" ) ,

	ANDROID_COMPILE( "androidCompile" , "src/android/actionscript" , "src/android/fonts" ) ,
	ANDROID_LIBRARY_COMPILE( "androidLibraryCompile" ) ,
	ANDROID_AS_LIBRARY_COMPILE( "androidAsLibraryCompile" ) ,
	ANDROID_NATIVE_COMPILE( "androidNativeCompile" ) ,
	ANDROID_PACKAGE( "androidPack" , "src/android/assets" ) ,

	DESKTOP_COMPILE( "desktopCompile" , "src/desktop/actionscript" , "src/desktop/fonts" ) ,
	DESKTOP_LIBRARY_COMPILE( "desktopLibraryCompile" ) ,
	DESKTOP_AS_LIBRARY_COMPILE( "desktopAsLibraryCompile" ) ,
	DESKTOP_NATIVE_COMPILE( "desktopNativeCompile" ) ,
	DESKTOP_PACKAGE( "desktopPack" , "src/desktop/assets" ) ,

	public static final List<Configurations> DEFAULTS = [ COMPILE ,
														  LIBRARY_COMPILE ,
														  AS_LIBRARY_COMPILE ,
														  NATIVE_COMPILE ,
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