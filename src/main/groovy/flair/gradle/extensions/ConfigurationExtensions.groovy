package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum ConfigurationExtensions {

	ADL( "adl" ) ,
	APP_DESCRIPTOR( "appDescriptor" )

	private String name

	ConfigurationExtensions( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}