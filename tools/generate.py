import json

SUBCLASS_OF = "SUBCLASSOF"
PART_OF = "http://purl.org/dc/terms/isPartOf"

CONCEPTS = {
    # symptoms
    "Symptom": "",
    "Cough": "Symptom",
    "Eye Issue": "Symptom",
    "Vision Loss": "Eye Issue",
    "Patchy spots in eyes": "Vision Loss",
    "Fever": "Symptom",
    "Cough": "Symptom",

    # time
    # "Time Period": "",
    # "Fast": "Time Period",
    # "Slow": "Time Period",
    # "One day": "Fast",
    # "One week": "Fast",
    # "One week": "Slow",
    # "One month": "Slow",

    "Diagnosis": "",
    "Glaucoma": "Diagnosis",
    "Open Angle Glaucoma": "Glaucoma",
    "COVID-19": "Diagnosis",
}

# this table is backwards, the relation is from the array items as sub to the diag as obj
DIAGNOSIS_REQUIREMENTS = {
    "Glaucoma": [ "Patchy spots in eyes" ],
    "COVID-19": [ "Cough", "Fever" ],
    
}

# langs
LANGS = ["English", "Hindi"]

# end header

output = {
    "nodes": [],
    "edges": []
}

last_concept_id = 10000
last_name_id = 20000
last_desc_id = 30000

lut = {}

for k, v in CONCEPTS.items():
    new_concept_uri = f"OATM{last_concept_id:06}"
    output["nodes"] += [{
        "uri": new_concept_uri,
        "content": f"_{k}",
    }]
    last_concept_id += 1

    lut[k] = new_concept_uri

    new_name_uri = f"OATM{last_name_id:06}"
    output["nodes"] += [{
        "uri": new_name_uri,
        "content": f"TODO: {k} title",
    }]
    last_name_id += 1

    new_desc_uri = f"OATM{last_desc_id:06}"
    output["nodes"] += [{
        "uri": new_desc_uri,
        "content": f"TODO: {k} description",
    }]
    last_desc_id += 1

    output["edges"] += [{
        "obj": new_concept_uri,
        "relation": "http://purl.org/dc/elements/1.1/description",
        "subj": new_desc_uri,
    }]

    if v != "":
        output["edges"] += [{
            "obj": lut[v],
            "relation": SUBCLASS_OF,
            "subj": new_concept_uri,
        }]

for d, values in DIAGNOSIS_REQUIREMENTS.items():
    for sv in values:
        output["edges"] += [{
            "subj": lut[sv],
            "relation": "http://purl.org/dc/elements/1.1/isPartOf",
            "obj": lut[sv],
        }]

# for f in sorted(L, key=lambda):

with open("../src/main/resources/ontology.json", "r+") as file:
    file.write(json.dumps(output, indent="    "))
