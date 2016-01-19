package flair.gradle.tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Task {
	CLEAN( "clean" , Clean , Group.BUILD ) ,
	//ASSEMBLE( "assemble" , null , Group.BUILD ) ,
	//COMPILE( "compile" , null , Group.BUILD ) ,
	PUBLISH_ATLASES( "publishAtlases" , PublishAtlases , Group.TEXTURE_PACKER ) ,
	INCREMENT_VERSION( "incrementVersion" , VersioningIncrementVersion , Group.DEFAULT ) ,

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