class DynamicDbConfigPropertyGrailsPlugin {
    // the plugin version
    def version = "0.0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Dynamic Db Config Property Plugin" // Headline display name of the plugin
    def author = "Guanyu Guo"
    def authorEmail = "ggy1986@163.com"
    def description = '''\
This plugin provides your application the ability to change the config properties without restarting the application. The values in Config.groovy are persisted in database and a set of interfaces manages frequently-used properties.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/dynamic-db-config-property"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
	def license = "APACHE"

    // Details of company behind the plugin (if there is one)
	def organization = [ name: "UOW", url: "uow.edu.au" ]

    // Any additional developers beyond the author specified above.
    def developers = [ [ name: "Peter Newnam", email: "pnewnam@uow.edu.au" ], [ name: "Zoran Stojakovic", email: "zorans@uow.edu.au" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "Github", url: "https://github.com/ggylyq1986/dynamic-db-config-property/issues" ]

    // Online location of the plugin's browseable source code.
	def scm = [ url: "https://github.com/ggylyq1986/dynamic-db-config-property" ]

}
