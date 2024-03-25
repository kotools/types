private val recommendedGroup: String = "recommended"
tasks.named("projects").configure { group = recommendedGroup }
tasks.named<TaskReportTask>("tasks").configure {
    group = recommendedGroup
    displayGroup = recommendedGroup
}
