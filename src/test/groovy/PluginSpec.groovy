import flair.gradle.plugins.*
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PluginSpec extends Specification
{
	Project project = new ProjectBuilder( ).withProjectDir( new File( "src/test/project" ) ).build( )

	def "apply Ios plugin"()
	{
		when:
		project.apply( plugin: "flair.ios" )

		then:
		project.plugins.find { it instanceof IosPlugin } != null
	}

	def "apply Android plugin"()
	{
		when:
		project.apply( plugin: "flair.android" )

		then:
		project.plugins.find { it instanceof AndroidPlugin } != null
	}

	def "apply Desktop plugin"()
	{
		when:
		project.apply( plugin: "flair.desktop" )

		then:
		project.plugins.find { it instanceof DesktopPlugin } != null
	}

	def "apply mobile plugins"()
	{
		when:
		project.apply( plugin: "flair.mobile" )

		then:
		project.plugins.find { it instanceof IosPlugin } != null
		project.plugins.find { it instanceof AndroidPlugin } != null
	}

	def "apply all platforms plugins"()
	{
		when:
		project.apply( plugin: "flair.all" )

		then:
		project.plugins.find { it instanceof IosPlugin } != null
		project.plugins.find { it instanceof AndroidPlugin } != null
		project.plugins.find { it instanceof DesktopPlugin } != null
	}

	def "apply TexturePacker plugins"()
	{
		when:
		project.apply( plugin: "flair.texturepacker" )

		then:
		project.plugins.find { it instanceof TexturePackerPlugin } != null
	}

	def "apply IDEA plugins"()
	{
		when:
		project.apply( plugin: "flair.idea" )

		then:
		project.plugins.find { it instanceof IdeaPlugin } != null
	}

	def "apply all plugins"()
	{
		when:
		project.apply( plugin: "flair.all" )
		project.apply( plugin: "flair.texturepacker" )
		project.apply( plugin: "flair.idea" )

		then:
		project.plugins.find { it instanceof IosPlugin } != null
		project.plugins.find { it instanceof AndroidPlugin } != null
		project.plugins.find { it instanceof DesktopPlugin } != null
		project.plugins.find { it instanceof TexturePackerPlugin } != null
		project.plugins.find { it instanceof IdeaPlugin } != null
	}
}
