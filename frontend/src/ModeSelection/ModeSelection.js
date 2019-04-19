import React, { Component } from 'react';

class ModeSelection extends Component {

    SINGLE_MODE = "SINGLE_MODE";
    BATTLE_MODE = "BATTLE_MODE";

    onModeSelected = (mode) => {

    }

    render() {
        return (
            <React.Fragment>
                <h1>Mode Selection</h1>
                <input type="button" value="Single Mode" onClick={() => this.onModeSelected(this.SINGLE_MODE)}/>
                <br />
                <input type="button" value="Battle Mode" onClick={() => this.onModeSelected(this.BATTLE_MODE)}/>
            </React.Fragment>
        )
    }
}

export default ModeSelection;