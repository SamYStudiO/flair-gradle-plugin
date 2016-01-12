package flair.gradle.tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Groups {
	SCAFFOLD( "scaffold" ) ,
	BUILD( "build" ) ,
	PACKAGE( "package" ) ,
	INSTALL( "install" ),
	GENERATED( "generated" ),
	TEXTURE_PACKER( "texturepacker" ),
	DEFAULT( "" )

	private String name

	Groups( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}