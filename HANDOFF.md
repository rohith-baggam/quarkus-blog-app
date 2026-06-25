# Quarkus Learning Handoff

## Who I Am
- 4 years Django/Python/PostgreSQL/Redis/Celery backend experience
- Basic Spring Boot knowledge
- Strong database and server knowledge
- Solution Architect and Backend Engineer by role

## Your Role
You are my TEACHER, not my code writer.
- Explain concepts first, then guide me to write code myself
- Always map Quarkus concepts to Django equivalents
- Ask me a question / let me guess BEFORE giving the answer
- Give hints when I'm stuck, not complete solutions
- One concept at a time
- Ask me to try first before helping
- Never write complete files for me

## Project Goal
Building a Blog Application in Quarkus to learn:
- Project setup and fundamentals
- SQLite + MySQL database integration
- Panache ORM (Hibernate)
- Flyway migrations
- MVC architecture (Resource → Service → Repository)
- JWT Authentication
- Role-based authorization
- File uploads
- Pagination, search, filtering
- WebSockets
- Docker + GraalVM native build

## Tech Stack
- Quarkus 3.36.3
- Java 21
- SQLite (dev), MySQL (prod)
- Panache ORM
- Flyway migrations
- SmallRye JWT
- WebSockets
- REST + Jackson

## Project Location
~/Desktop/stuff/java-learning/quarkus-learning/learn-by-doing/project/blog-app

## Project Package Structure
src/main/java/com/
  auth/model/          → auth related models (empty for now)
  blog/model/          → BlogPost.java, BlogPostImage.java
  common/model/        → BaseEntity.java
  learning/            → HealthResource.java, HealthReturnResponse.java
  user/model/          → User.java

src/main/resources/
  application.properties
  db/migration/
    V1__create_initial_tables.sql

## Dependencies in pom.xml
- quarkus-rest
- quarkus-rest-jackson
- quarkus-hibernate-orm-panache
- quarkus-jdbc-mysql (added at project creation, sqlite added manually)
- quarkus-flyway
- quarkus-smallrye-jwt
- quarkus-websockets
- quarkus-smallrye-openapi
- quarkus-hibernate-validator (added for @NotBlank, @Email, @Valid)
- quarkus-elytron-security-common (added for BcryptUtil password hashing)
- sqlite-jdbc (org.xerial)
- hibernate-community-dialects

## Progress Completed
✅ Step 1 - Project created with Quarkus CLI
✅ Step 2 - HealthResource working on /api/health
✅ Step 3 - Swagger UI working at /swagger-ui
✅ Step 4 - DTO pattern learned (HealthReturnResponse)
✅ Step 5 - Models created: BaseEntity (@MappedSuperclass), User, BlogPost, BlogPostImage
✅ Step 6 - V1 Flyway migration SQL written and running
✅ Step 7 - Server starts successfully, SQLite DB migrated
✅ Step 8 - MVC pattern: Resource, Service, Repository layers built for User
✅ Step 9 - User registration API working end-to-end
✅ Step 10 - User login API working, JWT token generated (RS256, 7 days expiry)
✅ Step 11 - JwtUtil created in com.utils (static utility pattern)

## What's Next
- Standardized API response wrapper (ApiResponse<T>) like CoreGenericUtils.success_response
- Global exception handling with ExceptionMapper (remove try/catch from every Resource)
- Protect endpoints with @RolesAllowed / @Authenticated
- Blog post CRUD API

## Understanding Tracker
Track understanding level per area. Review and revise any ⚠️ or ❌ areas at project end.

| Area | Level | Notes |
|---|---|---|
| Project setup & Quarkus CLI | ✅ Solid | No issues |
| application.properties config | ✅ Solid | Mapped to Django settings.py |
| Flyway migrations | ✅ Solid | Understands can't modify after run |
| @Entity / @MappedSuperclass | ✅ Solid | Figured out @MappedSuperclass independently after hint |
| @JoinColumn / FK relationships | ✅ Solid | Understood after Django ForeignKey comparison |
| SQL snake_case vs Java camelCase | ✅ Solid | Identified column name mismatch independently |
| @Column(name=) mapping | ✅ Solid | Understands why it exists |
| MVC layer separation (why) | ✅ Solid | Built registration + login end-to-end independently |
| PanacheRepository | ✅ Solid | Used findByEmail, orElseThrow, persist correctly |
| CDI / @Inject / @ApplicationScoped | ⚠️ Developing | Seen @Inject, not yet comfortable with CDI bean lifecycle |
| Jakarta Validation (@NotBlank etc) | ✅ Solid | Used correctly, understands it replaces is_valid() |
| @Transactional | ✅ Solid | Understands WHY writes need it, WHY reads don't |
| DTO pattern (Request/Response) | ✅ Solid | Wrote Request + Response DTOs independently |
| Optional<T> | ✅ Solid | Understood isEmpty vs null, orElseThrow pattern |
| Static utility vs CDI bean | ⚠️ Developing | Understands the difference, not yet comfortable with CDI |
| JWT generation (SmallRye) | ✅ Solid | Built JwtUtil, understands RS256 vs HS256 tradeoffs |
| JWT authentication | ❌ Not started | |
| Role-based authorization | ❌ Not started | |
| ExceptionMapper (global error handling) | ❌ Not started | |
| Pagination / filtering | ❌ Not started | |
| File uploads | ❌ Not started | |
| WebSockets | ❌ Not started | |
| Docker + GraalVM native | ❌ Not started | |

## Key Django → Quarkus Mappings Learned So Far
| Django | Quarkus |
|---|---|
| models.py | Entity class with @Entity |
| models.Model | extends PanacheEntityBase |
| auto_now_add | @PrePersist |
| auto_now | @PreUpdate |
| ForeignKey | @ManyToOne |
| abstract = True in Meta | @MappedSuperclass |
| migrations/ | db/migration/ (Flyway SQL files) |
| makemigrations | You write SQL manually |
| migrate | Runs on startup via flyway.migrate-at-start=true |
| settings.py | application.properties |
| requirements.txt | pom.xml |
| urls.py | @Path on Resource class |
| views.py | Resource class |
| serializers.py | DTO class or Java Record |
