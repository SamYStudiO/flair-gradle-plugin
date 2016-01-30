package flair.gradle.utils

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class CliDevicesOutputParser
{
	public CliDevicesOutputParser()
	{
	}

	public List<String> parse( String adtOutput )
	{
		List<String> list = new ArrayList<String>( )

		boolean startExtraction = false

		adtOutput.eachLine {

			if( startExtraction ) list.add( it.split( "\\s" )[ 0 ] )
			if( it == "List of devices attached" ) startExtraction = true
		}

		return list
	}
}
