package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IConfigurationContainerExtension extends IConfigurationExtension
{
	public IConfigurationExtension getConfiguration( String name )

	public IConfigurationExtension getAdl()

	public IConfigurationExtension getAppDescriptor()
}