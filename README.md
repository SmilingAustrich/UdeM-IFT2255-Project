# Ma Ville

A console application for tracking public road work and street obstructions in
Montreal. Residents look up what is being dug up near them and open requests for
work on their own property; contractors browse those requests, bid on them, and
file projects of their own.

Work and obstruction data comes from the City of Montreal's open data portal
over CKAN's `datastore_search` API. Accounts and requests are held in memory for
the lifetime of the process.

## What it does

**Residents**

- Register and sign in
- List current and upcoming road work, filtered by borough or by category
- Search that work by identifier, category or borough
- List street obstructions, filtered by the work that caused them or by street
- Open a work request against their own property, and track or close it
- Set a notification area beyond their own borough

**Contractors**

- Register with an eight digit city accreditation number, and sign in
- Browse residents' open requests, filtered by category, borough or start date
- Apply for a request, withdraw an application, or confirm one
- File a project, with a warning when its hours clash with the borough's stated
  preference
- Propose working hours

## Data

Two CKAN datasets are read at runtime:

| Dataset | Resource id | Fields used |
| --- | --- | --- |
| Road work | `cc41b532-f12d-40fb-9f55-eb58c9a2b12b` | `id`, `boroughid`, `reason_category`, `organizationname` |
| Obstructions | `a2bc8014-488c-495d-941b-e7ae1999d1bd` | `id_request`, `shortname`, `streetimpacttype` |

`ObstructionRecord.workId` is the road work's `id`, so obstructions join back to
the work that caused them. A field the portal omits or sends as JSON null is read
as `"N/A"`, because the screens print these values directly.

The in-process store holds three maps: residents by email, contractors by email,
and one open work request per resident. It is seeded with test accounts at
startup and nothing is written to disk, so state does not survive a restart.

## Layout

```
com.maville
├── model      Resident, Intervenant, ResidentialWorkRequest, WorkType, User
├── data       OpenDataSource and the Montreal open data client, WorkRecord,
│              ObstructionRecord
├── auth       registration and password checking
├── database   the in-memory store
└── ui         the screens
    └── console  terminal colours, and reading and validating one answer
```

Screens depend on the `OpenDataSource` interface rather than on the HTTP client,
so they can be driven from a fixed list of records rather than the live API.

## Build and run

Requires JDK 17 or newer and Maven.

```sh
mvn package
java -jar target/ma-ville-1.0.0.jar
```

Sign in with `resident@prototype.com` / `password123`, or
`intervenant@prototype.com` / `password456`.

Accents render correctly only under a UTF-8 locale. If the menus show `?` where
`é` should be, run with `LANG=C.UTF-8` or pass `-Dstdout.encoding=UTF-8`.

## Tests

```sh
mvn test      # 35 tests, no network and no terminal needed
mvn verify    # also runs MontrealOpenDataIT against the live city API
```

`mvn test` is offline on purpose. Parsing is separated from fetching, so the
field mapping is covered against fixtures, and the check that the city has not
renamed a column lives in `MontrealOpenDataIT`, which runs under failsafe. A
failure there is a real signal, but not one that should break a build on a
machine with no route to `donnees.montreal.ca`.

## Design

Use case, class, C4, activity and sequence diagrams are in
[`docs/diagrams/`](docs/diagrams/).

## Authors

Tarik Hireche, Karim Ndoye, Ilyesse Bouzammita.
