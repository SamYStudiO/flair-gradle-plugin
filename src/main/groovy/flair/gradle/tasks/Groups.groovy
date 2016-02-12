package flair.gradle.tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Groups {
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

	public Groups( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}