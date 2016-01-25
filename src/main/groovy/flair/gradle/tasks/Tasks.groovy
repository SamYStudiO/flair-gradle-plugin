package flair.gradle.tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Tasks {
	CLEAN( "clean" , Clean , Groups.BUILD ) ,
	ASSEMBLE( "assemble" , Assemble , Groups.BUILD ) ,
	COMPILE( "compile" , Compile , Groups.BUILD ) ,
	PACKAGE( "package" , Package , Groups.PACKAGE ) ,
	INSTALL( "install" , Install , Groups.INSTALL ) ,
	UNINSTALL( "uninstall" , Uninstall , Groups.UNINSTALL ) ,
	LAUNCH_EMULATOR( "launchAdl" , LaunchEmulator , Groups.LAUNCH ) ,
	PUBLISH_ATLASES( "publishAtlases" , PublishAtlases , Groups.TEXTURE_PACKER ) ,
	ASDOC( "asdoc" , Asdoc , Groups.DOCUMENTATION ),
	LIST_DEVICES( "listDevices" , ListDevices , Groups.INSTALL )

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