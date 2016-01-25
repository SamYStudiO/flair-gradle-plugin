package flair.gradle.tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Tasks {
	CLEAN( "clean" , Clean , Groups.BUILD ) ,
	ASSEMBLE( "assemble" , null , Groups.BUILD ) ,
	COMPILE( "compile" , null , Groups.BUILD ) ,
	PACKAGE( "package" , null , Groups.PACKAGE ) ,
	INSTALL( "install" , null , Groups.INSTALL ) ,
	LAUNCH_ADL( "launchAdl" , null , Groups.LAUNCH ) ,
	PUBLISH_ATLASES( "publishAtlases" , PublishAtlases , Groups.TEXTURE_PACKER )

	private String name

	private Class type

	private Groups group

	public Tasks( String name , Class type , Groups group )
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