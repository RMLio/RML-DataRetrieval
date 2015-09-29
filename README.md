RML Data Retrieval
==================

RML Data Retrieval implementation supports the [RML Processor](https://github.com/mmlab/RMLProcessor/) to access and retrieve the data sources.

The existing implementation supports different dataset and service descriptions for data access in RDF.

The access interfaces supported at the moment are 
* Dump files 
* [DCAT](http://www.w3.org/TR/vocab-dcat/) for Data Catalogues (RML Data Retrieval considers Dataset and Distribution descriptions)
* [Hydra](http://www.w3.org/ns/hydra/spec/latest/core/) for Web APIs (RML Data Retrieval considers IRI Template and Paged Collection descriptions)
* subset of [D2RQ](http://d2rq.org/d2rq-language) for Database Access Interface (RML Data Retrieval considers Database description)
* [SPARQL-SD](http://www.w3.org/TR/sparql11-service-description/) for SPARQL Service (RML Data Retrieval considers Dataset and Service descriptions)
* [VoID](http://www.w3.org/TR/void/) for accessing RDF Datasets and/or their SPARQL endpoint (RML Data Retrieval considers Dataset and Dataset Description descriptions)
* [CSVW](http://www.w3.org/TR/tabular-metadata/) for CSV files published on the Web (RML Data Retrieval considers Table and Dialect Description descriptions)

Details and examples of possible data source descriptions and their alignment with RML can be found at [RML Data Sources] (http://rml.io/RMLdataRetrieval.html).

Related Publication
-------------------

Anastasia Dimou, Ruben Verborgh, Miel Vander Sande, Erik Mannens, Rik Van de Walle

[Machine-Interpretable Dataset and Service Descriptions for Heterogeneous Data Access and Retrieval](http://dl.acm.org/citation.cfm?id=2814873)

In Proceedings of the 11th International Conference on Semantic Systems, SEMANTiCS2015


More Information
----------------

More information about the solution can be found at http://rml.io

This application is developed by Multimedia Lab http://www.mmlab.be

Copyright 2015, Multimedia Lab - Ghent University - iMinds

License
-------

The RMLProcessor is released under the terms of the [MIT license](http://opensource.org/licenses/mit-license.html).
