package flair.gradle.utils

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PropertyFile
{
	private Properties properties = new Properties( )

	private File file

	public File getFile()
	{
		return file
	}

	public setFile( File file )
	{
		this.file = file

		if( file.exists( ) ) properties.load( file.newDataInputStream( ) )
	}

	public PropertyFile( String path )
	{
		this( new File( path ) )
	}

	public PropertyFile( File file )
	{
		setFile( file )
	}

	public String getProp( String name )
	{
		return properties.getProperty( name )
	}
}
