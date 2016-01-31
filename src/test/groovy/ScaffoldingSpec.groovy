import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.GradleRunner
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ScaffoldingSpec extends Specification
{

	/*def "scaffold project"()
	{
		setup:
		TemporaryFolder temp = new TemporaryFolder( )
		temp.create( )

		println( temp.root )
		Project project = new ProjectBuilder( ).withProjectDir( temp.root ).build( )
		File buildFile = temp.newFile( "build.gradle" )

		and:
		buildFile << """
            apply plugin: "flair.ios"

            flair{
            	packageName "com.hello.world"
            }
        """

		when:

		GradleRunner.create( )
				.withProjectDir( temp.root )
				.build( )

		then:
		project.file( "app" ).exists( )
	}*/
}
