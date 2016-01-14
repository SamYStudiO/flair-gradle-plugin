package flair.gradle.tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Group {
	SCAFFOLD( "scaffold" ) ,
	BUILD( "build" ) ,
	PACKAGE( "package" ) ,
	INSTALL( "install" ),
	LAUNCH( "launch" ),
	GENERATED( "generated" ),
	TEXTURE_PACKER( "texturepacker" ),
	DEFAULT( "" )

	private String name

	Group( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}