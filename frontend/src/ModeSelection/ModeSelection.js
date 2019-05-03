import React, { Component } from 'react';

const EASY_MODE = "EASY_MODE";
const MID_MODE = "MID_MODE";
const BATTLE_MODE = "BATTLE_MODE";

class ModeSelection extends Component {

    state = {
        selecting_level: false
    }

    onModeSelected = (mode) => {
        console.log("user chose:", mode);
        this.props.history.push('/game', { mode })
    }

    render() {

        let mode_selection = (
            <div>
                <h1>Mode Selection</h1>
                <input type="button" value="Single Mode" onClick={() => this.setState({selecting_level: true})} />
                <br />
                <input type="button" value="Battle Mode" onClick={() => this.onModeSelected(BATTLE_MODE)} />
            </div>
        );

        let level_selection = (
            <div>
                <h1>Mode Selection</h1>
                <input type="button" value="Easy" onClick={() => this.onModeSelected(EASY_MODE)} />
                <br />
                <input type="button" value="Medium" onClick={() => this.onModeSelected(MID_MODE)} />
            </div>
        )

        return (
            <React.Fragment>
                {this.state.selecting_level ? level_selection : mode_selection}
            </React.Fragment>
        )
    }
}

// export default ModeSelection;
export { ModeSelection as default, EASY_MODE, MID_MODE, BATTLE_MODE };