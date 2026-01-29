# TP3 – Architecture Microservices (REST)
## Gestion d’un Cabinet Médical

Cours assuré par : **Jaouad OUHSSAINE**  
Contact : jaouad.ouhs@gmail.com | jaouad_ouhssaine@um5.ac.ma

---

## Contexte

Ce TP correspond à la **troisième phase** du projet pédagogique évolutif  
**Gestion d’un Cabinet Médical**.

Il consiste à **faire évoluer l’architecture SOA du TP2** vers une  
**architecture microservices REST**, basée sur des microservices **totalement autonomes**, chacun disposant de sa **propre API** et de sa **propre base de données**, avec un **API Gateway** comme point d’entrée unique.

---

## Objectifs du TP

- Mettre en place une architecture **microservices**
- Découpler totalement les services (code et données)
- Supprimer tout module de persistance partagé
- Introduire un **API Gateway** pour l’exposition des APIs
- Mettre en œuvre la communication **REST inter-services**
- Comprendre la différence entre **SOA et Microservices**

---

## Architecture globale

L’architecture est basée sur :
- Des **microservices métiers autonomes** (Patient, Médecin, Rendez-vous, Consultation)
- Un **service composite** pour l’agrégation des données (Dossier Patient)
- Un **API Gateway** servant de point d’entrée unique pour les clients externes
- Une **base de données par microservice**

Les clients n’accèdent jamais directement aux microservices, toutes les requêtes passent par le Gateway.

---

## Structure du projet

```text
cabinetMedicalTp3MS/
│
├── api-gateway                  # API Gateway (point d’entrée externe)
│
├── patient-service              # Microservice Patient (API + DB)
├── medecin-service              # Microservice Médecin (API + DB)
├── rendezvous-service           # Microservice Rendez-vous (API + DB)
├── consultation-service         # Microservice Consultation (API + DB)
│
├── dossier-service              # Service composite (agrégation REST)
│
└── pom.xml                      # Projet parent (packaging pom)
