package flair.gradle.tasks

import flair.gradle.cli.Adt
import flair.gradle.cli.ICli
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Certificate extends AbstractTask
{
	public String cname

	public String orgUnit

	public String orgName

	public String country

	public int years = 30

	public String type = "2048-RSA"

	public String password

	public File output

	public Certificate()
	{
		group = Groups.SIGNING.name
		description = ""
	}

	@TaskAction
	public void generate()
	{

		ICli adt = new Adt( )

		adt.addArgument( "-certificate" )
		adt.addArgument( "-cn" )
		adt.addArgument( cname )
		if( orgUnit )
		{
			adt.addArgument( "-ou" )
			adt.addArgument( orgUnit )
		}
		if( orgName )
		{
			adt.addArgument( "-o" )
			adt.addArgument( orgName )
		}
		if( country )
		{
			adt.addArgument( "-c" )
			adt.addArgument( country )
		}
		adt.addArgument( "-validityPeriod" )
		adt.addArgument( "${ years }" )
		adt.addArgument( type )
		adt.addArgument( output.path )
		adt.addArgument( password )

		adt.execute( project )
	}
}
