package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantExtension extends IPlatformExtension
{
	String getDimension()

	void dimension( String dimension )
}