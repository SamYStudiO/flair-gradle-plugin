package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum ConfigurationExtension {

	ADL( "adl" ) ,
	APP_DESCRIPTOR( "appDescriptor" )

	private String name

	ConfigurationExtension( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}