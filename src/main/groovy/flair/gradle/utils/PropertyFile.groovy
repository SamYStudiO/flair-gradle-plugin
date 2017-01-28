package flair.gradle.utils

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PropertyFile
{
	private Properties properties = new Properties( )

	private File file

	File getFile()
	{
		return file
	}

	void setFile( File file )
	{
		this.file = file

		if( file && file.exists( ) ) properties.load( file.newDataInputStream( ) )
	}

	PropertyFile( String path )
	{
		this( new File( path ) )
	}

	PropertyFile( File file )
	{
		setFile( file )
	}

	String getProp( String name )
	{
		return properties.getProperty( name )
	}
}
