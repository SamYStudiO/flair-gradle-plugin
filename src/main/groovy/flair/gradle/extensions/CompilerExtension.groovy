package flair.gradle.extensions

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class CompilerExtension extends AbstractExtension
{
	private String actionscriptFileEncoding

	private Boolean benchmark

	private Boolean debug

	private String define

	private String dumpConfig

	private String externs

	private String externalLibraryPath

	private String fontsAdvancedAntiAliasing

	public CompilerExtension( String name , Project project )
	{
		super( name , project )
	}

	@Override
	public Object getProp( String property , boolean returnDefaultIfNull )
	{
		if( this[ property ] || !returnDefaultIfNull ) return this[ property ] else
		{
			switch( property )
			{
				case Properties.ACTIONSCRIPT_FILE_ENCODING.name: return null
				case Properties.BENCHMARK.name: return true
				case Properties.DEBUG.name: return false
				case Properties.DUMP_CONFIG.name: return null
				case Properties.EXTERNS.name: return null
				case Properties.FONTS_ADVANCED_ANTI_ALIASING.name: return false

				default: return null
			}
		}
	}
}
