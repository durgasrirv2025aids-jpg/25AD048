const API = "";


// ==========================
// DASHBOARD
// ==========================

async function loadDashboard() {

    try {

        const teams =
            await fetch("/api/teams")
                .then(response => response.json());


        const fixtures =
            await fetch("/api/fixtures")
                .then(response => response.json());


        const standings =
            await fetch("/api/standings")
                .then(response => response.json());


        document.getElementById("teamCount")
            .textContent = teams.length;


        document.getElementById("fixtureCount")
            .textContent = fixtures.length;


        const completed =
            fixtures.filter(
                fixture =>
                    String(fixture.status)
                        .toLowerCase() === "completed"
            );


        document.getElementById("completedCount")
            .textContent = completed.length;


        let highestPoints = 0;


        standings.forEach(
            standing => {

                if (standing.points > highestPoints) {

                    highestPoints =
                        standing.points;

                }

            }
        );


        document.getElementById("topPoints")
            .textContent = highestPoints;


    } catch (error) {

        console.log(error);

    }

}


// ==========================
// ADD TEAM
// ==========================

async function addTeam(event) {

    event.preventDefault();


    const name =
        document.getElementById("teamName")
            .value;


    const department =
        document.getElementById("department")
            .value;


    try {

        const response =
            await fetch("/api/teams", {

                method: "POST",

                headers: {
                    "Content-Type":
                        "application/json"
                },

                body: JSON.stringify({

                    name: name,

                    department: department

                })

            });


        if (!response.ok) {

            throw new Error();

        }


        document.getElementById("teamMessage")
            .textContent =
            "Team registered successfully.";


        document.getElementById("teamForm")
            .reset();


        loadTeams();


    } catch (error) {

        document.getElementById("teamMessage")
            .textContent =
            "Failed to register team.";

    }

}


// ==========================
// LOAD TEAMS
// ==========================

async function loadTeams() {

    const table =
        document.getElementById("teamTable");


    if (!table) return;


    try {

        const response =
            await fetch("/api/teams");


        const teams =
            await response.json();


        table.innerHTML = "";


        if (teams.length === 0) {

            table.innerHTML =
                `<tr>
                    <td colspan="4">
                        No teams registered.
                    </td>
                 </tr>`;

            return;

        }


        teams.forEach(team => {

            const row =
                document.createElement("tr");


            row.innerHTML = `

                <td>${team.id}</td>

                <td>
                    <b>${team.name}</b>
                </td>

                <td>
                    ${team.department}
                </td>

                <td>

                    <button
                        class="button light"
                        onclick="deleteTeam(${team.id})">

                        Delete

                    </button>

                </td>

            `;


            table.appendChild(row);

        });


    } catch (error) {

        table.innerHTML =
            `<tr>
                <td colspan="4">
                    Unable to load teams.
                </td>
             </tr>`;

    }

}


// ==========================
// DELETE TEAM
// ==========================

async function deleteTeam(id) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete this team?"
        );


    if (!confirmDelete) return;


    try {

        await fetch(
            `/api/teams/${id}`,
            {
                method: "DELETE"
            }
        );


        loadTeams();


    } catch (error) {

        alert(
            "Unable to delete team."
        );

    }

}


// ==========================
// LOAD FIXTURES
// ==========================

async function loadFixtures() {

    const table =
        document.getElementById("fixtureTable");


    if (!table) return;


    try {

        const response =
            await fetch("/api/fixtures");


        const fixtures =
            await response.json();


        table.innerHTML = "";


        if (fixtures.length === 0) {

            table.innerHTML =
                `<tr>
                    <td colspan="5">
                        No fixtures generated.
                    </td>
                 </tr>`;

            return;

        }


        fixtures.forEach(
            (fixture, index) => {

                const row =
                    document.createElement("tr");


                row.innerHTML = `

                    <td>
                        ${index + 1}
                    </td>

                    <td>
                        ${fixture.team1Id}
                    </td>

                    <td>
                        ${fixture.team2Id}
                    </td>

                    <td>
                        ${fixture.matchDate}
                    </td>

                    <td>
                        ${fixture.status}
                    </td>

                `;


                table.appendChild(row);

            }
        );


    } catch (error) {

        table.innerHTML =
            `<tr>
                <td colspan="5">
                    Unable to load fixtures.
                </td>
             </tr>`;

    }

}


// ==========================
// GENERATE FIXTURES
// ==========================

async function generateFixtures() {

    const message =
        document.getElementById(
            "fixtureMessage"
        );


    try {

        const response =
            await fetch(
                "/api/fixtures/generate",
                {
                    method: "POST"
                }
            );


        const result =
            await response.text();


        message.textContent =
            result;


        loadFixtures();


    } catch (error) {

        message.textContent =
            "Failed to generate fixtures.";

    }

}


// ==========================
// LOAD STANDINGS
// ==========================

async function loadStandings() {

    const table =
        document.getElementById(
            "standingTable"
        );


    if (!table) return;


    try {

        const response =
            await fetch(
                "/api/standings"
            );


        const standings =
            await response.json();


        standings.sort(
            (a, b) =>
                b.points - a.points
        );


        table.innerHTML = "";


        if (standings.length === 0) {

            table.innerHTML =
                `<tr>
                    <td colspan="7">
                        No standings available.
                    </td>
                 </tr>`;

            return;

        }


        standings.forEach(
            (standing, index) => {

                const row =
                    document.createElement("tr");


                row.innerHTML = `

                    <td>
                        <b>${index + 1}</b>
                    </td>

                    <td>
                        ${standing.teamId}
                    </td>

                    <td>
                        ${standing.played}
                    </td>

                    <td>
                        ${standing.won}
                    </td>

                    <td>
                        ${standing.drawn}
                    </td>

                    <td>
                        ${standing.lost}
                    </td>

                    <td>
                        <b>
                            ${standing.points}
                        </b>
                    </td>

                `;


                table.appendChild(row);

            }
        );


    } catch (error) {

        table.innerHTML =
            `<tr>
                <td colspan="7">
                    Unable to load standings.
                </td>
             </tr>`;

    }

}


// ==========================
// UPDATE RESULT
// ==========================

async function updateResult() {

    const teamId =
        document.getElementById(
            "resultTeamId"
        ).value;


    const result =
        document.getElementById(
            "resultType"
        ).value;


    const message =
        document.getElementById(
            "resultMessage"
        );


    if (!teamId) {

        message.textContent =
            "Please enter Team ID.";

        return;

    }


    try {

        const response =
            await fetch(
                `/api/standings/${teamId}/${result}`,
                {
                    method: "PUT"
                }
            );


        if (!response.ok) {

            throw new Error();

        }


        message.textContent =
            `Team ${teamId} updated with ${result}.`;


        loadStandings();


    } catch (error) {

        message.textContent =
            "Unable to update result.";

    }

}


// ==========================
// ADD MATCH
// ==========================

async function addMatch() {

    const team1Id =
        document.getElementById(
            "matchTeam1"
        ).value;


    const team2Id =
        document.getElementById(
            "matchTeam2"
        ).value;


    const matchDate =
        document.getElementById(
            "matchDate"
        ).value;


    const status =
        document.getElementById(
            "matchStatus"
        ).value;


    const message =
        document.getElementById(
            "matchMessage"
        );


    // Check input

    if (!team1Id ||
        !team2Id ||
        !matchDate) {

        message.textContent =
            "Please fill all match details.";

        return;

    }


    // Team 1 and Team 2 should not be same

    if (team1Id === team2Id) {

        message.textContent =
            "Team 1 and Team 2 cannot be same.";

        return;

    }


    const matchData = {

        team1Id: Number(team1Id),

        team2Id: Number(team2Id),

        matchDate: matchDate,

        status: status

    };


    try {

        const response =
            await fetch(
                "/api/matches",
                {

                    method: "POST",

                    headers: {

                        "Content-Type":
                            "application/json"

                    },

                    body:
                        JSON.stringify(matchData)

                }
            );


        if (!response.ok) {

            throw new Error();

        }


        message.textContent =
            "Match saved successfully.";


        document.getElementById(
            "matchTeam1"
        ).value = "";


        document.getElementById(
            "matchTeam2"
        ).value = "";


        document.getElementById(
            "matchDate"
        ).value = "";


        document.getElementById(
            "matchStatus"
        ).value = "Scheduled";


        loadMatches();


    } catch (error) {

        console.error(error);

        message.textContent =
            "Unable to save match.";

    }

}


// ==========================
// LOAD MATCHES
// ==========================

async function loadMatches() {

    const table =
        document.getElementById(
            "matchTableBody"
        );


    if (!table) return;


    try {

        const response =
            await fetch(
                "/api/matches"
            );


        if (!response.ok) {

            throw new Error();

        }


        const matches =
            await response.json();


        table.innerHTML = "";


        if (matches.length === 0) {

            table.innerHTML =
                `<tr>

                    <td colspan="5">
                        No matches available.
                    </td>

                </tr>`;

            return;

        }


        matches.forEach(match => {

            const row =
                document.createElement("tr");


            row.innerHTML = `

                <td>
                    ${match.id}
                </td>

                <td>
                    Team ${match.team1Id}
                </td>

                <td>
                    Team ${match.team2Id}
                </td>

                <td>
                    ${match.matchDate}
                </td>

                <td>
                    ${match.status}
                </td>

            `;


            table.appendChild(row);

        });


    } catch (error) {

        console.error(error);


        table.innerHTML =
            `<tr>

                <td colspan="5">
                    Unable to load matches.
                </td>

            </tr>`;

    }

}


// ==========================
// PAGE LOAD
// ==========================

document.addEventListener(
    "DOMContentLoaded",
    function () {


        // Dashboard

        if (
            document.getElementById("teamCount")
        ) {

            loadDashboard();

        }


        // Teams

        if (
            document.getElementById("teamTable")
        ) {

            loadTeams();

        }


        // Fixtures

        if (
            document.getElementById("fixtureTable")
        ) {

            loadFixtures();

        }


        // Matches

        if (
            document.getElementById("matchTableBody")
        ) {

            loadMatches();

        }


        // Standings

        if (
            document.getElementById(
                "standingTable"
            )
        ) {

            loadStandings();

        }

    }
);