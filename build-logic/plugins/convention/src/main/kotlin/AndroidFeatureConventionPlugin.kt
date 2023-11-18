import com.android.build.gradle.LibraryExtension
import com.najudoryeong.musicdo.configureGradleManagedDevices
import com.najudoryeong.musicdo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("musicdo.android.library")
            apply("musicdo.android.library.compose")
            apply("musicdo.android.hilt")
        }

        dependencies {
            add("implementation", project(":core:ui"))


            add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
            add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
        }
    }
}
