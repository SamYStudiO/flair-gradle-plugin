package flair.gradle.tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Task {
	CLEAN( "clean" , Clean , Group.BUILD ) ,
	ASSEMBLE( "assemble" , null , Group.BUILD ) ,
	COMPILE( "compile" , null , Group.BUILD ) ,
	PUBLISH_ATLASES( "publishAtlases" , PublishAtlases , Group.TEXTURE_PACKER ) ,
	INCREMENT_VERSION( "incrementVersion" , VersioningIncrementVersion , Group.DEFAULT ) ,
	GENERATE_FONT_CLASS( "generateFontClass" , GenerateFontClass , Group.GENERATED ) ,
	AUTO_GENERATE_FONT_CLASS( "autoGenerateFontClass" , AutoGenerateFontClass , Group.GENERATED ) ,
	GENERATE_RESOURCE_CLASS( "generateResourceClass" , GenerateResourceClass , Group.GENERATED ) ,
	AUTO_GENERATE_RESOURCE_CLASS( "autoGenerateResourceClass" , AutoGenerateResourceClass , Group.GENERATED )

	private String name

	private Class type

	private Group group

	Task( String name , Class type , Group group )
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

	public Group getGroup()
	{
		return group
	}
}