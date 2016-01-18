package flair.gradle.tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Group {
	SCAFFOLD( "scaffold" ) ,
	BUILD( "build" ) ,
	ASSEMBLE( "assemble" ) ,
	COMPILE( "compile" ) ,
	PACKAGE( "package" ) ,
	INSTALL( "install" ) ,
	PROCESS( "process" ) ,
	LAUNCH( "launch" ) ,
	GENERATED( "generated" ) ,
	TEXTURE_PACKER( "texturepacker" ) ,
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