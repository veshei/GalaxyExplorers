# GalaxyExplorers
Every attendee of the Conference of Galactic Explorers taking place in the year 9016 has submitted a list of galaxies they have visited in the previous year (the barrier of the speed of light was broken in the year 3046). For each galaxy they have visited, the explorers are eager to provide a report describing what they saw during their stay. You, as the organizer, would like to compile a volume of reports (the proceedings of the conference) in which every galaxy is described exactly once by any of the explorers who have visited it. Hence you examine the lists of galaxies provided by the explorers in order to appoint a number of them to deliver a single report on one of the galaxies they have visited. You want to choose them in such a way so that every galaxy visited by at least one of the explorers is represented, and that every galaxy is reported on exactly once. In other words, even though a given galaxy G may have been visited by a number of explorers, you must ensure that no two of those that you appointed will report on G. Similarly, an explorer E may have visited many galaxies, so when you appoint E you must ensure that E reports on only one of them. For example, you may have the following situation:
explorer 1 has visited galaxies A, C
explorer 2 has visited galaxies A, B
explorer 3 has visited only the galaxy B
You appoint therefore

1 to report on C
2 to report on A,
3 to report on B.
Given an arbitrary set of explorers and their lists of visited galaxies, you are asked to implement an algorithm which appoints a number of them to report on a single galaxy in such a way that every galaxy is represented (if such an appointment turns out not to exist, your algorithm should detect that).

