#! /usr/bin/env python

import json

TITLE = "title"
SUBCLASS_OF = "is_a"
SYMPTOM_OF = "symptom_of"
DESCRIPTION = "description"
QUESTION_FOR = "question_for"
ANSWER_FOR = "answer_for"
BOOL_QUESTION = "bool_question"

CONCEPTS = {
    # symptoms
    "symptom": ("", "Do you have symptoms?"),
    "ear_issue": ("symptom", "Does your ear also have symptoms?"),
    "eye_issue": ("symptom", "Does your eye also have symptoms?"),
    "mouth_issue": ("symptom", "Does your mouth also have symptoms?"),
    "nose_issue": ("symptom", "Does your nose have symptoms?"),
    "skin_issue": ("symptom", "Do you have skin issues?"),

    "cough": ("mouth_issue", "Do you also have a cough?"),
    "jaw_pain": ("mouth_issue", "Do you also have jaw pain?"),
    "sore_throat": ("mouth_issue", "Do you also have a sore throat?"),

    "hearing_loss": ("ear_issue", "Do you have hearing loss?"),
    "ear_ringing": ("ear_issue", "Do you have ringing in your ears?"),
    "ear_hissing": ("ear_issue", "Do you hear hissing in your ears?"),



    "vision_loss": ("eye_issue", "Are you also experiencing vision loss?"),
    "eye_pain": ("eye_issue", "Are you experiencing eye pain?"),
    "red_eyes": ("eye_issue", "Do you have red or bloodshot eyes?"),
    "patchy_spots_in_vision": ("eye_issue", "Are there patchy spots in your peripheral vision?"),
    "blurry_vision": ("eye_issue", "Is your vision blurry?"),
    "eye_pressure": ("eye_issue", "Is there pressure in your eyes?"),

    "stuffy_nose": ("nose_issue", "Do you have a stuffy nose?"),
    "runny_nose": ("nose_issue", "Do you have a runny nose?"),
    "nose_bleed": ("nose_issue", "Have you had a nosebleed?"),
    "sneezing": ("nose_issue", "Do you sneeze a lot?"),


    "skin_rash": ("skin_issue", "Do you have a rash?"),
    "itching": ("skin_issue", "Do you feel itchy?"),
    "raised_welts": ("skin_issue", "Are there raised welts on your skin?"),

    # diagnosis
    "diagnosis": ("", ""),
    "glaucoma": ("diagnosis", ""),
    "dry_eye": ("diagnosis", ""),
    "sinusitis": ("diagnosis", ""),
    "otitis_media": ("diagnosis", ""),
    "tinnitus": ("diagnosis", ""),
    "conjunctivitis": ("diagnosis", ""),
    "eczema": ("diagnosis", ""),
    "welts": ("diagnosis", ""),
    "allergy": ("diagnosis", ""),
}

# this table is backwards, the relation is from the array items as sub to the diag as obj
DIAGNOSIS_REQUIREMENTS = {
    "glaucoma": ["patchy_spots_in_vision", "vision_loss", "eye_pressure", "eye_pain"],
    "dry_eye": ["eye_pain", "vision_loss", "blurry_vision", "red_eyes"],
    "sinusitis": ["stuffy_nose", "runny_nose"],
    "allergy": ["stuffy_nose", "runny_nose", "sneezing"],
    "otitis_media": ["ear_ringing", "hearing_loss"],
    "tinnitus": ["ear_hissing", "ear_ringing"],
    "conjunctivitis": ["red_eyes", "eye_pain"],
    "eczema": ["skin_rash", "itching"],
    "diagnosis": ["itching", "raised_welts"],

}

QUESTIONS = {
    ("symptom", "Where is your worst symptom?"): {
        "eye_issue": "My eye.",
        "ear_issue": "My ear.",
        "mouth_issue": "My mouth.",
        "nose_issue": "My nose.",
        "skin_issue": "My skin.",
    },
    ("mouth_issue", "What kind of mouth issue?"): {
        "cough": "I have a cough.",
        "jaw_pain": "My jaw hurts.",
        "sore_throat": "I have a sore throat.",
    },
    ("ear_issue", "What kind of ear issue?"): {
        "hearing_loss": "I can't hear well.",
        "ear_ringing": "I hear ringing.",
    },
    ("eye_issue", "What kind of eye issue?"): {
        "vision_loss": "I can't see well.",
        "eye_pain": "My eye hurts.",
        "red_eyes": "My eyes are red.",
    },
    ("nose_issue", "What kind of nose issue?"): {
        "stuffy_nose": "My nose is stuffy.",
        "runny_nose": "My nose is runny.",
        "nose_bleed": "I've had a nosebleed.",
    },
    ("skin_issue", "What kind of skin issue?"): {
        "skin_rash": "I have a rash.",
        "itching": "I feel itchy.",
    },
}


# end header
output = {
    "nodes": [],
    "edges": []
}

last_concept_id = 10000
last_name_id = 20000
last_desc_id = 30000
last_question_id = 40000
last_response_id = 50000

lut = {}

for concept, (parent, bool_question) in CONCEPTS.items():
    new_concept_uri = f"OATM{last_concept_id:06}"
    output["nodes"] += [{
        "uri": new_concept_uri,
        "content": f"_{concept}",
    }]
    last_concept_id += 1

    lut[concept] = new_concept_uri

    new_name_uri = f"OATM{last_name_id:06}"
    output["nodes"] += [{
        "uri": new_name_uri,
        "content": f"TODO: {concept} title",
    }]
    last_name_id += 1

    new_desc_uri = f"OATM{last_desc_id:06}"
    output["nodes"] += [{
        "uri": new_desc_uri,
        "content": f"TODO: {concept} description",
    }]
    last_desc_id += 1

    if bool_question != "":
        new_bool_question_uri = f"OATM{last_question_id:06}"
        output["nodes"] += [{
            "uri": new_bool_question_uri,
            "content": bool_question,
        }]
        output["edges"] += [{
            "obj": new_concept_uri,
            "relation": BOOL_QUESTION,
            "subj": new_bool_question_uri,
        }]
        last_question_id += 1

    output["edges"] += [{
        "obj": new_concept_uri,
        "relation": DESCRIPTION,
        "subj": new_desc_uri,
    }]

    if parent != "":
        output["edges"] += [{
            "obj": lut[parent],
            "relation": SUBCLASS_OF,
            "subj": new_concept_uri,
        }]

for diagnosis, symptoms in DIAGNOSIS_REQUIREMENTS.items():
    for symptom in symptoms:
        output["edges"] += [{
            "subj": lut[diagnosis],
            "relation": SYMPTOM_OF,
            "obj": lut[symptom],
        }]
#         output["edges"] += [{
#             "obj": lut[diagnosis],
#             "relation": SYMPTOM_OF,
#             "subj": lut[symptom],
#         }]

for (parent, question), responses in QUESTIONS.items():
    new_question_uri = f"OATM{last_question_id:06}"
    output["nodes"] += [{
        "uri": new_question_uri,
        "content": question,
    }]
    last_question_id += 1
    output["edges"] += [{
        "subj": new_question_uri,
        "relation": QUESTION_FOR,
        "obj": lut[parent],
    }]
    for node, response in responses.items():
        new_response_uri = f"OATM{last_response_id:06}"
        output["nodes"] += [{
            "uri": new_response_uri,
            "content": response,
        }]
        last_response_id += 1
        output["edges"] += [{
            "subj": new_response_uri,
            "relation": ANSWER_FOR,
            "obj": lut[node],
        }]

with open("../src/main/resources/ontology.json", "w+") as file:
    file.write(json.dumps(output, indent="    "))
