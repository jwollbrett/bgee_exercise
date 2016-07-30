The aim of this exercise is to retrieve homologous structures: i) with a developmental origin that changed during evolution, i.e., homologous structures that do not develop from the same organ in different species; ii) with a partonomy relation that varies between species. 

## Outline
 
* You will use the annotations provided by Bgee to retrieve anatomical entities that are homologous, see https://github.com/BgeeDB/anatomical-similarity-annotations/wiki/Similarity-annotations
* You will use the Uberon `composite-metazoan` ontology in OWL to retrieve relations between anatomical entities, see http://uberon.github.io/downloads.html#multiont
* You will use the bgee-pipeline library, owltools and OWLAPI to perform this task (all included in the provided fat jar). 
* Java 7 or 8 as you want (but you need to compile in Java 8, as the Bgee pipeline is written in Java 8).

## Instructions 

* using the bgee-pipeline library, extract the similarity annotations from the file `https://raw.githubusercontent.com/BgeeDB/anatomical-similarity-annotations/master/release/ancestral_taxa_homology_annotations.tsv` using the method `org.bgee.pipeline.annotations.SimilarityAnnotationUtils.extractAncestralTaxaAnnotations(String)`

* from the ontology `composite-metazoan.owl`, and using owltools 0.3-SNAPSHOT and OWLAPI 4.2.5: 
  * extract the relations with the object property "develops_from" (see http://purl.obolibrary.org/obo/RO_0002202), **or any of its sub-properties**, outgoing from anatomical entities present in the similarity annotation file 
  * similarly, extract the relations with the object property "part_of" (see http://purl.obolibrary.org/obo/BFO_0000050), **or any of its sub-properties**

(see in owltools the methods `owltools.graph.OWLGraphWrapperExtended.getOWLClassByIdentifier(String)`, `owltools.graph.OWLGraphWrapperEdgesExtended.getOutgoingEdgesWithGCI(OWLObject)`, `owltools.graph.OWLGraphEdge.getTarget()`, `owltools.graph.OWLGraphEdge.getSingleQuantifiedProperty()`, and `owltools.graph.OWLGraphEdge.getGCIFiller()`; see OWLAPI documentation for related classes)

* Identify homologous structures having ancestors through "develops_from" object properties (or any of its subproperties) that vary between species, if any. 
* Identify homologous structures having ancestors through "part_of" object properties (or any of its subproperties) that vary between species, if any.

* Provide the result in a text format of your choice, allowing an easy retrieval of the relevant information. Highlight the most significant results, if any, in a 1/2 text page. Provide the results on a fork of this project on github. 
