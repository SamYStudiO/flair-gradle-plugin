package flair.gradle.tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Tasks {

	SCAFFOLD( "generateProject" , Scaffold , Groups.SCAFFOLD ) ,
	UPDATE_PROPERTIES( "updateProperties" , UpdateProperties , Groups.SCAFFOLD ) ,
	PROCESS_IOS_RESOURCES( "processIOSResources" , ProcessIOSResources , Groups.DEFAULT ) ,
	PROCESS_ANDROID_RESOURCES( "processAndroidResources" , ProcessAndroidResources , Groups.DEFAULT ) ,
	PROCESS_DESKTOP_RESOURCES( "processDesktopResources" , ProcessDesktopResources , Groups.DEFAULT ) ,
	PROCESS_PROD_IOS_RESOURCES( "processProdIOSResources" , ProcessProdIOSResources , Groups.DEFAULT ) ,
	PROCESS_PROD_ANDROID_RESOURCES( "processProdAndroidResources" , ProcessProdAndroidResources , Groups.DEFAULT ) ,
	PROCESS_PROD_DESKTOP_RESOURCES( "processProdDesktopResources" , ProcessProdDesktopResources , Groups.DEFAULT ) ,
	PUBLISH_ATLASES( "publishAtlases" , TexturePacker , Groups.TEXTURE_PACKER ) ,
	INCREMENT_VERSION( "incrementVersion" , VersioningIncrementVersion , Groups.DEFAULT ) ,
	GENERATE_FONT_CLASS( "generateFontClass" , GenerateFontClass , Groups.GENERATED ) ,
	AUTO_GENERATE_FONT_CLASS( "autoGenerateFontClass" , AutoGenerateFontClass , Groups.GENERATED ) ,
	GENERATE_RESOURCE_CLASS( "generateResourceClass" , GenerateResourceClass , Groups.GENERATED ) ,
	AUTO_GENERATE_RESOURCE_CLASS( "autoGenerateResourceClass" , AutoGenerateResourceClass , Groups.GENERATED )

	private String name

	private Class type

	private Groups group

	Tasks( String name , Class type , Groups group )
	{
		this.name = name
		this.type = type
		this.group = group
	}

	public String getName()
	{
		return name
	}

	public Class getType()
	{
		return type
	}

	public Groups getGroup()
	{
		return group
	}
}