//Tutorial used: https://reactarmory.com/guides/learn-react-by-itself/react-basics
//This frontend was directly copied from Spn-ThreatViz.

//Shorthand for creating an element
//First argument is the type (a/div/etc), second argument is element's attributes/props, remaining are children elements
const rce = React.createElement;

/* ---------- Standard Site Elements ---------- */

//Website banner
const banner = rce('div', 
    {className: 'slaheader'},
    rce('p', {}, "Sparen LangAgent")
)

//Website Information
const subbanner = rce('div', 
    {className: 'slactrtxt'},
    rce('p', {}, 
        "Sparen LangAgent Application Monitoring"
    )
)

//Footer
const footer = rce('footer', 
    {className: 'slactrtxt'},
    rce('a', {href: 'https://github.com/Sparen/Spn-LangAgent'}, "Source Code (GitHub)")
)

/* ---------- Primary Components ---------- */

//Metric Component. Takes in the pure JSON output and creates tables
class MetricComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            output: []
        };
        //Bind all non-React methods that access local variables
        this.runMetric = this.runMetric.bind(this);
    }

    componentDidMount() {
    }

    render() {
        let that = this; //for access within closures
        const { error, isLoaded, output, sortField } = this.state;
        if (error) {
            return (
                rce('div', 
                    {className: 'slactrtxt'},
                    rce('p', {}, "There was an error with the search.")
                )
            )
        } else if (!isLoaded) {
            //Begin with basic metrics
            var basicstats = rce('div', {},
                rce('p', {}, "Total Requests: N/A"),
                rce('p', {}, "Average Request Time: N/A"),
                rce('p', {}, "Average Memory Allocated: N/A"),
                rce('p', {}, "Average Strings Created: N/A")
            );

            //Generate and format all the rows and contents, and create the table element to insert.
            var header = rce('thead', {},
                rce('tr', {className: 'tablerow'},
                    rce('th', {className: 'tableelement', "Request URL"),
                    rce('th', {className: 'tableelement', "Request Time"),
                    rce('th', {className: 'tableelement', "Strings Created"),
                    rce('th', {className: 'tableelement', "Memory Allocated")
                )
            );
            //No body to view yet
            return (
                rce('div', 
                    {className: 'slactrtxt'},
                    basicstats
                    rce('br', {}),
                    tabletoinsert
                )
            )
        } else {
            //Begin with basic metrics
            var basicstats = rce('div', {},
                rce('p', {}, "Total Requests: TODO"),
                rce('p', {}, "Average Request Time: TODO"),
                rce('p', {}, "Average Memory Allocated: TODO"),
                rce('p', {}, "Average Strings Created: TODO")
            );

            //Generate and format all the rows and contents, and create the table element to insert.
            //Header: 
            var header = rce('thead', {},
                rce('tr', {className: 'tablerow'},
                    rce('th', {className: 'tableheader', "Request URL"),
                    rce('th', {className: 'tableheader', "Request Time"),
                    rce('th', {className: 'tableheader', "Strings Created"),
                    rce('th', {className: 'tableheader', "Memory Allocated")
                )
            );
            var rows = [];
            for (var i = 0; i < output.length; i += 1) {
                var currrequest = output[i];
                var rurl = currrequest.requestURL;
                var rtime = currrequest.requestTime;
                var rstrings = currrequest.requestStrings;
                var rmemory = currrequest.requestMemory;
                //First row for this entry
                var rowobj1 = rce('tr', {className: 'tablerow'},
                    rce('td', {className: 'tableelement'}, returnNAIfEmptyString(rurl)),
                    rce('td', {className: 'tableelement'}, returnNAIfEmptyString(rtime)),
                    rce('td', {className: 'tableelement'}, returnNAIfEmptyString(rstrings)),
                    rce('td', {className: 'tableelement'}, returnNAIfEmptyString(rmemory))
                );
                rows.push(rowobj1);
            }
            var tabletoinsert = rce('table', {className: 'slatable'},
                header,
                rce('tbody', {}, rows)
            );
            return (
                rce('div', 
                    {className: 'slactrtxt'},
                    basicstats
                    rce('br', {}),
                    tabletoinsert
                )
            )
        }
    }

    runMetric() {
        var apicall = "/api/sla/metrics";
        fetch(apicall)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        output: result,
                        error: null //Reset in case errored before; this allows future queries to go through.
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
    }

}

//Main container for the application (Old)
const container = rce('div', 
    {},
    banner,
    subbanner,
    rce(MetricComponent, {}),
    rce('br', {}),
    footer
)

//Main container for the application
class AppContainer extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }

    render() {
        var active = this.state.active;
        var panel = rce(MetricComponent, {});
        return (
            rce('div', 
                {},
                banner,
                subbanner,
                rce('br', {}),
                panel,
                rce('br', {}),
                footer
            )
        );
     }
};

//Render the container
ReactDOM.render(
    rce(AppContainer, {}),
    document.getElementById('app')
)

/* ---------- Helper Functions ---------- */

function returnNAIfEmptyString(str){
    if (str == "") {
        return "N/A";
    } else {
        return str;
    }
}

//Wrapper for sortByField. If array is already sorted, reverses order. Otherwise sorts using sortByField
function sortOutputByField(field, arr) {
    var sorted = true;
    for (var i = 0; i < arr.length - 1; i += 1) {
        if (arr[i][field] > arr[i + 1][field]) {
            sorted = false;
        }
    }
    if (sorted) {
        console.log("sortOutputByField: returning reversed array");
        return arr.reverse();
    } else {
        console.log("sortOutputByField: now sorting.");
        var newarr = sortByField(field, arr, 0, arr.length - 1);
        return newarr;
    }
}

//Sorts the output array by the given field in place using quick sort.
function sortByField(field, arr, left, right) {
    var len = arr.length;
    var pivot;
    var partitionIndex;

    if (left < right) {
        pivot = right; //set pivot
        partitionIndex = sortPartition(field, arr, pivot, left, right);
        sortByField(field, arr, left, partitionIndex - 1);
        sortByField(field, arr, partitionIndex + 1, right);
    }
    return arr;
}

function sortPartition(field, arr, pivot, left, right){
    //console.log("TEST: arr[pivot]: " + JSON.stringify(arr[pivot]));
    var pivotValue = arr[pivot][field];
    var partitionIndex = left;

    for(var i = left; i < right; i++){
        if(arr[i][field] < pivotValue){
            swap(arr, i, partitionIndex);
            partitionIndex++;
        }
    }
    swap(arr, right, partitionIndex);
    return partitionIndex;
}

function swap(arr, i, j){
    var temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}

//Returns Array in string form, delimited by "; "
function ArrayToString(arr) {
    var str = "";
    for (var j = 0; j < arr.length; j += 1) {
        str += arr[j];
        if (j != arr.length - 1) {
            str += "; ";
        }
    }
    return str;
}