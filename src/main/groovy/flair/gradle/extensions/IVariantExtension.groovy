package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IVariantExtension extends IPlatformExtension
{
	String getDimension()

	void dimension( String dimension )
}