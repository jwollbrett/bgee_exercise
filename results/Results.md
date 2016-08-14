## Part_of metrics
Using part_of property (and subproperties) we found 258 ancestors of homology annotations. 66 of them contained variations (~25%).
* Main groups of species found for the same homology entity : 
   * Homo sapiens, Rattus norvegicus, Mus musculus
   * Danio, Xenopus, Drosophila melanogaster
* Over represented species : Danio, Xenopus (~50%)
* Rarely represented species : C. elegans

## Develop_from metrics
Using developed_from property (and subproperties) we found 419 ancestors for homology annotations. 60 of them contained variations (~14%).
* Over represented species : Danio, Xenopus, Homo sapiens (~90%)
* Rarely represented species : Mus musculus, Gallus, Drosophila melanogaster

## Results
In the result files we can distinginsh 3 different kind of homologous structures having ancestors through object properties (or any of its subproperties) that vary between species :
In addition to these 3 categories. The result file can also help to detect misannotation. For instance in our result file *mouth* has *mouth* as ancestor.

### 1. Ancestors associated to species outside of the homology
Sometimes homologous structures have ancestors associated to species outsite of the homology. For instance, the entity *periderm* is annotated as homologous for Amniota. But *periderm* has *skin epidermis* as ancestor for Xenopus, and *ectoderm* as ancestor for Danio. Both of these species are out of Amniota. May be it's possibile to generalise the homologous structure annotation and, in this example, define the homology at the first common ancestor of both Xenopus and Danio (which is Euteleostomi).

### 2. Ancestors associated to species inside of the homology and coming from same subpart of the ontology

These results are the most abundant in the result file.

They can be the result of merging species specific ontologies into one unique ontology. For instance the entities *zygote stage*, *cleavage stage*, *gastrula stage*, and *organogenesis stage* have ancestors through *part_of* property that look identical. These ancestors are embryonic stage specific to each species (Mus musculus, Homo Sapiens, Rattus norvegicus) and coming from species specific ontologies. It could be interesting to edit these ancestors. For instance it could be possible to use the generic UBERON entity *embryonic stage* (UBERON:0000068)

They can also correspond to different of point of view between annotator. For instance the *cranial nerve II* (optic nerve) entity is *part of* *camera type eye* for Xenopus and *diencephalic white matter* for Danio. The focus is different but it seems logic that optical nerve is part of eye and part of diencephalum.


### 3. Ancestors associated to species inside of the homology and coming from different subpart of the ontology

Finally, some hologous structural annotations have ancestors which vary between species. For instance, *female gonad* is homologous for Vertebrata and is defined as *in_lateral_side* of *intestinal genitalia* for Mammalia, and defined as *part_of* endocrine systeme* for Danio. As the first ancestor corresponds to an organ and the second one corresponds to an anatomic system, it's maybe possible to add both *intestinal genitalia* annotation for Danio and *endocrine system* for Mammalia.

