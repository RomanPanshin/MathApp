from flask import Flask, jsonify

app = Flask(__name__)

# Sample data to serve
math_problems = [
    {"id": 1, "problem": "What is 2 + 2?", "solution": "4"},
    {"id": 2, "problem": "What is 3 * 4?", "solution": "12"},
    # Add more math problems as needed
]

@app.route('/math_problems', methods=['GET'])
def get_math_problems():
    return jsonify(math_problems)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=8000)

