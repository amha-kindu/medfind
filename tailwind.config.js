module.exports = {
	mode: process.env.NODE_ENV ? "jit" : undefined,
	purge: ["./src/**/*.html"],
	darkMode: "media", // or 'media' or 'class'
	theme: {
		extend: {
			colors: {
				lightgreen: "#86D7DC",
				darkgreen: "#097076",
			},
		},
	},
	variants: {
		extend: {},
	},
	plugins: [],
};
