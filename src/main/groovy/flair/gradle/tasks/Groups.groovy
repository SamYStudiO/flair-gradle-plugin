package flair.gradle.tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Groups {
	BUILD( "build" ) ,
	PACKAGE( "package" ) ,
	INSTALL( "install" ) ,
	LAUNCH( "launch" ) ,
	TEXTURE_PACKER( "texturepacker" ) ,
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