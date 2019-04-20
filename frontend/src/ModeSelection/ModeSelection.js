import React, { Component } from 'react';

const SINGLE_MODE = "SINGLE_MODE";
const BATTLE_MODE = "BATTLE_MODE";

class ModeSelection extends Component {

    onModeSelected = (mode) => {
        console.log("user chose:", mode);
        this.props.history.push('/game', { mode })
    }

    render() {
        return (
            <React.Fragment>
                <h1>Mode Selection</h1>
                <input type="button" value="Single Mode" onClick={() => this.onModeSelected(SINGLE_MODE)} />
                <br />
                <input type="button" value="Battle Mode" onClick={() => this.onModeSelected(BATTLE_MODE)} />
            </React.Fragment>
        )
    }
}

// export default ModeSelection;
export { ModeSelection as default, SINGLE_MODE, BATTLE_MODE };