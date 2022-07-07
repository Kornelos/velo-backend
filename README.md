# Velo 

###### This repository stores the backend component, for the frontend component please visit [velo-frontend](https://github.com/julzerinos/velo-frontend).

Velo is the fruit of labour project created for [Kornel](github.com/Kornelos/)'s and [Julian](github.com/julzerinos/)'s Bachelor's Thesis. The application is a standalone atheltic performance anaylsis platform targeted towards cyclists.

Due to strict regulations involving the copyright of the work created under the watchful wing of the University, the code is shared under an equally strict [CC BY-NC-ND 4. license](https://creativecommons.org/licenses/by-nc-nd/4.0/). Please review the license before using the source code. 

The following sections contain paraphrased excerpts from the Thesis to provide insight for the technical project.

## Application summary

Velo is a web application created for the use case of advanced, customizable and extendable
analysis of cycling training by cycling coaches and/or cyclists themselves. The application should
allow user profile creation which will be used by both athletes and coaches. The coach may
subscribe to an athlete (after their permission has been received) which grants access to athlete
training data.

The coach may use predefined formulae and data visualization blocks to analyze training
progress. These may include (among others) a single training view, averages over a selected time
period and the training power curve. Various data visualization tools will be used (line charts,
graphs, raw data extracts).

The coach is permitted access to a scripting module which they may use to define custom
scripts for the data visualization blocks or create variations of the existing predefined scripts.
The coach is allowed (under the condition that he is subscribed to multiple athletes) to overlay
and/or compare the data visualization results of multiple athletes. This will be used by cycling
team coaches for contextual data analysis.

The athlete is allowed to manually upload data or connect external data collection APIs
(eg. Strava) which will allow for automated data ingestion by the application without requiring
athlete prompting.

This application will store the data it collects to allow for a faster and easier data processing
flow. The calculations for the specific data scripts in the application (selected by the user) will
be performed on the client-side (i.e. frontend).
