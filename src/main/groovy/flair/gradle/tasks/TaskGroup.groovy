package flair.gradle.tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
enum TaskGroup {
	BUILD( "build" ) ,
	PACKAGE( "package" ) ,
	INSTALL( "install" ) ,
	UNINSTALL( "uninstall" ) ,
	LAUNCH( "launch" ) ,
	TEXTURE_PACKER( "texturepacker" ) ,
	DOCUMENTATION( "documentation" ) ,
	SIGNING( "signing" ) ,
	DEVICES( "devices" ) ,
	GENERATED( "generated" ) ,
	PROCESS( "" ) ,
	DEFAULT( "" )

	private String name

	TaskGroup( String name )
	{
		this.name = name
	}

	String getName()
	{
		return name
	}
}